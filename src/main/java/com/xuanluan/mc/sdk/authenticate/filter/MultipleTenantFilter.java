package com.xuanluan.mc.sdk.authenticate.filter;

import com.xuanluan.mc.sdk.service.constant.BaseConstant;
import com.xuanluan.mc.sdk.service.tenant.ITenantIdentifierResolver;
import com.xuanluan.mc.sdk.utils.StringUtils;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Consumer;

public abstract class MultipleTenantFilter extends BaseProcessFilter {
    protected final ITenantIdentifierResolver tenantIdentifierResolver;
    private String tenant;

    protected MultipleTenantFilter(ITenantIdentifierResolver tenantIdentifierResolver) {
        this.tenantIdentifierResolver = tenantIdentifierResolver;
    }

    protected abstract String getTenantHeader();

    protected abstract Consumer<HttpServletRequest> beforeSwitchTenant();

    protected abstract Consumer<HttpServletRequest> afterSwitchTenant();

    @Override
    protected void process(HttpServletRequest request) {
        Assert.isTrue(StringUtils.hasText(getTenantHeader()), "tenant header must not be blank");

        beforeSwitchTenant().accept(request);
        //switch tenant
        tenantIdentifierResolver.setCurrentTenant(getTenant());
        afterSwitchTenant().accept(request);
    }

    protected String getTenant() {
        return StringUtils.hasText(tenant) ? tenant : BaseConstant.TENANT;
    }

    protected void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
