package org.xuanluan.mc.sdk.authenticate.service.impl;

import com.xuanluan.mc.sdk.generate.service.jwt.JwtRSAProvider;
import com.xuanluan.mc.sdk.generate.utils.KeyPairUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;
import org.xuanluan.mc.sdk.authenticate.domain.model.CurrentUserSession;
import org.xuanluan.mc.sdk.authenticate.service.ICurrentUserService;
import org.xuanluan.mc.sdk.authenticate.service.constant.CacheNameConstant;

import java.security.PublicKey;
import java.util.function.Supplier;

@CacheConfig(cacheNames = CacheNameConstant.currentUser, cacheManager = "currentUserCacheManager")
public class CurrentUserServiceImpl implements ICurrentUserService {
    private final PublicKey publicKey;

    public CurrentUserServiceImpl(@Value("${jwt.secret.login.public}") String publicKey) {
        this.publicKey = KeyPairUtils.publicKey(publicKey);
    }

    @Cacheable(value = "sessions", key = "#clientId+'/'+#token")
    @Override
    public CurrentUserSession get(String clientId, String token, Supplier<CurrentUserSession> userSupplier) {
        //validate token
        JwtRSAProvider.decode(token, publicKey);

        CurrentUserSession currentUserSession = userSupplier.get();
        Assert.notNull(currentUserSession, "current user must not null");
        return currentUserSession;
    }
}
