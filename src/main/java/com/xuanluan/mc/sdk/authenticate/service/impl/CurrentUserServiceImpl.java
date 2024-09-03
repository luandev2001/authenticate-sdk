package com.xuanluan.mc.sdk.authenticate.service.impl;

import com.xuanluan.mc.sdk.authenticate.domain.model.CurrentUser;
import com.xuanluan.mc.sdk.authenticate.service.ICurrentUserService;
import com.xuanluan.mc.sdk.authenticate.service.constant.CacheNameConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class CurrentUserServiceImpl implements ICurrentUserService {
    private final CacheManager cacheManager;

    @Override
    public CurrentUser putIfAbsent(String clientId, String token, Supplier<CurrentUser> userSupplier) {
        Assert.notNull(clientId, "client must not null");
        Assert.notNull(token, "token must not null");

        String cacheKey = String.format("%s:%s", clientId, token);
        CurrentUser currentUser = getCache().get(cacheKey, CurrentUser.class);
        if (currentUser != null) getCache().put(cacheKey, userSupplier.get());
        return currentUser;
    }

    private Cache getCache() {
        return cacheManager.getCache(CacheNameConstant.currentUser);
    }
}
