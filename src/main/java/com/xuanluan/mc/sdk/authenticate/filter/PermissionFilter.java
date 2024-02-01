package com.xuanluan.mc.sdk.authenticate.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuanluan.mc.sdk.generate.service.jwt.JwtRSAProvider;
import com.xuanluan.mc.sdk.service.tenant.TenantIdentifierResolver;
import com.xuanluan.mc.sdk.utils.StringUtils;
import lombok.Getter;
import org.springframework.util.Assert;
import com.xuanluan.mc.sdk.authenticate.domain.model.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@Getter
public abstract class PermissionFilter extends MultipleTenantFilter {
    private CurrentUser currentUser = new CurrentUser();

    protected PermissionFilter(ObjectMapper objectMapper, TenantIdentifierResolver tenantIdentifierResolver) {
        super(objectMapper, tenantIdentifierResolver);
    }

    protected abstract String getAuthorizationHeader();

    protected abstract Predicate<HttpServletRequest> noAuthorityRequired();

    protected abstract Predicate<HttpServletRequest> authorityRequired();

    protected abstract PublicKey getPublicKey();

    protected abstract Function<String, CurrentUser> processCurrentUser();

    protected Consumer<HttpServletRequest> afterSwitchTenant() {
        return (request) -> {
            Assert.isTrue(StringUtils.hasText(getAuthorizationHeader()), "authorization header must not be blank");

            if (!noAuthorityRequired().test(request)) {
                String token = request.getHeader(getAuthorizationHeader());
                //validate token
                JwtRSAProvider.decode(token, getPublicKey());
                this.currentUser = processCurrentUser().apply(token);
                Assert.isTrue(authorityRequired().test(request), "you do not have authority access to the request");
            }
        };
    }
}
