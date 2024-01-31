package com.xuanluan.mc.sdk.authenticate.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuanluan.mc.sdk.authenticate.domain.model.CurrentClient;
import com.xuanluan.mc.sdk.service.constant.BaseConstant;
import com.xuanluan.mc.sdk.service.tenant.TenantIdentifierResolver;
import com.xuanluan.mc.sdk.utils.StringUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class MultipleTenantFilter extends BaseProcessFilter {
    private final TenantIdentifierResolver tenantIdentifierResolver;

    private String clientId;

    protected MultipleTenantFilter(ObjectMapper objectMapper, TenantIdentifierResolver tenantIdentifierResolver) {
        super(objectMapper);
        this.tenantIdentifierResolver = tenantIdentifierResolver;
    }

    protected abstract Function<String, CurrentClient> processCurrentClient();

    protected abstract String getTenantHeader();

    protected abstract Consumer<HttpServletRequest> afterSwitchTenant();

    @Override
    protected void process(HttpServletRequest request) {
        Assert.isTrue(StringUtils.hasText(getTenantHeader()), "tenant header must not be blank");

        setClientId(request.getHeader(getTenantHeader()));
        CurrentClient currentClient = processCurrentClient().apply(clientId);
        //switch tenant
        tenantIdentifierResolver.setCurrentTenant(currentClient.getId());
        afterSwitchTenant().accept(request);
    }

    protected String getClientId() {
        return this.clientId;
    }

    private void setClientId(String clientId) {
        this.clientId = StringUtils.hasText(clientId) ? clientId : BaseConstant.clientId;
    }
}
