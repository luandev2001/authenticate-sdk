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
    @Cacheable(key = "#clientId+'/'+#token")
    @Override
    public CurrentUser get(String clientId, String token, Supplier<CurrentUser> userSupplier) {
        CurrentUser currentUser = userSupplier.get();
        Assert.notNull(currentUser, "current user must not null");
        return currentUser;
    }
}
