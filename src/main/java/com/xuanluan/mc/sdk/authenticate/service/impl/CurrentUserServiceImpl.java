package com.xuanluan.mc.sdk.authenticate.service.impl;

import com.xuanluan.mc.sdk.authenticate.domain.model.CurrentUser;
import com.xuanluan.mc.sdk.authenticate.service.ICurrentUserService;
import com.xuanluan.mc.sdk.authenticate.service.constant.CacheNameConstant;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;

import java.util.function.Supplier;

@CacheConfig(cacheNames = CacheNameConstant.currentUser, cacheManager = "currentUserCacheManager")
public class CurrentUserServiceImpl implements ICurrentUserService {

    @Cacheable(key = "#clientId + ':' + #token ")
    @Override
    public CurrentUser putIfAbsent(String clientId, String token, Supplier<CurrentUser> userSupplier) {
        Assert.notNull(clientId, "client must not null");
        Assert.notNull(token, "token must not null");
        return userSupplier.get();
    }
}
