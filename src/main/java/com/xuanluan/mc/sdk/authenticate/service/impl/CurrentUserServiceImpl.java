package com.xuanluan.mc.sdk.authenticate.service.impl;

import com.xuanluan.mc.sdk.authenticate.domain.model.CurrentUser;
import com.xuanluan.mc.sdk.authenticate.service.ICurrentUserService;
import com.xuanluan.mc.sdk.authenticate.service.constant.CacheNameConstant;
import com.xuanluan.mc.sdk.service.builder.CacheBuilder;
import com.xuanluan.mc.sdk.utils.StringUtils;
import org.springframework.cache.CacheManager;

import java.util.function.Supplier;

public class CurrentUserServiceImpl implements ICurrentUserService {
    private final CacheBuilder<CurrentUser> cacheBuilder;

    public CurrentUserServiceImpl(CacheManager cacheManager) {
        cacheBuilder = new CacheBuilder<>(cacheManager, CacheNameConstant.currentUser, CurrentUser.class);
    }

    @Override
    public CurrentUser get(String clientId, String token) {
        return cacheBuilder.get(StringUtils.toKey(clientId, token));
    }

    @Override
    public CurrentUser putIfAbsent(String clientId, String token, Supplier<CurrentUser> userSupplier) {
        return cacheBuilder.putIfAbsent(StringUtils.toKey(clientId, token), userSupplier);
    }
}
