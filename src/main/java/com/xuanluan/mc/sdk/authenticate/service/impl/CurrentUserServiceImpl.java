package com.xuanluan.mc.sdk.authenticate.service.impl;

import com.xuanluan.mc.sdk.authenticate.domain.model.CurrentUser;
import com.xuanluan.mc.sdk.authenticate.service.ICurrentUserService;
import com.xuanluan.mc.sdk.authenticate.service.constant.CacheNameConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class CurrentUserServiceImpl implements ICurrentUserService {
    private final CacheManager cacheManager;

    @Override
    public CurrentUser get(String clientId, String token) {
        return getCache().get(getKeyCache(clientId, token), CurrentUser.class);
    }

    @Override
    public CurrentUser putIfAbsent(String clientId, String token, Supplier<CurrentUser> userSupplier) {
        CurrentUser currentUser = get(clientId, token);
        if (currentUser == null) getCache().put(getKeyCache(clientId, token), userSupplier.get());
        return currentUser;
    }

    private String getKeyCache(String clientId, String token) {
        return String.join(":", clientId, token);
    }

    private Cache getCache() {
        return cacheManager.getCache(CacheNameConstant.currentUser);
    }
}
