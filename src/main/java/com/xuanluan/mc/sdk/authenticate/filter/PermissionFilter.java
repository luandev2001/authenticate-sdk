package com.xuanluan.mc.sdk.authenticate.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuanluan.mc.sdk.authenticate.service.ICurrentClientService;
import com.xuanluan.mc.sdk.generate.service.jwt.JwtRSAProvider;
import com.xuanluan.mc.sdk.service.tenant.TenantIdentifierResolver;
import com.xuanluan.mc.sdk.utils.StringUtils;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import org.springframework.util.Assert;
import com.xuanluan.mc.sdk.authenticate.domain.model.CurrentUser;
import com.xuanluan.mc.sdk.authenticate.service.ICurrentUserService;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class PermissionFilter extends MultipleTenantFilter {
    private final ICurrentUserService currentUserService;
    @Getter
    private CurrentUser currentUser;

    protected PermissionFilter(ObjectMapper objectMapper, ICurrentClientService currentClientService, TenantIdentifierResolver tenantIdentifierResolver, ICurrentUserService currentUserService) {
        super(objectMapper, currentClientService, tenantIdentifierResolver);
        this.currentUserService = currentUserService;
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
                Claims claims = JwtRSAProvider.decode(token, getPublicKey());
                String username = claims.getSubject();
                Assert.isTrue(StringUtils.hasText(username), "username must not be blank");
                this.currentUser = currentUserService.get(getClientId(), token, () -> processCurrentUser().apply(username));
                Assert.isTrue(authorityRequired().test(request),"you do not have authority access to the request");
            }
        };
    }
}
