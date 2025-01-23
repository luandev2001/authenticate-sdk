package com.xuanluan.mc.sdk.authenticate.service.imp;

import com.xuanluan.mc.sdk.authenticate.model.CurrentUser;
import com.xuanluan.mc.sdk.authenticate.service.ICurrentUserService;
import com.xuanluan.mc.sdk.authenticate.service.constant.CacheNameConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.Assert;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class CurrentUserServiceImp implements ICurrentUserService {
    private final CacheManager cacheManager;

    @Override
    public CurrentUser putIfAbsent(String tenant, String token, Supplier<CurrentUser> userSupplier) {
        Assert.notNull(tenant, "tenant must not null");
        Assert.notNull(token, "token must not null");

        String cacheKey = String.format("%s:%s", tenant, token);
        CurrentUser currentUser = getCache().get(cacheKey, CurrentUser.class);
        if (currentUser == null) {
            currentUser = userSupplier.get();
            getCache().put(cacheKey, currentUser);
        }
        return currentUser;
    }

    private Cache getCache() {
        return cacheManager.getCache(CacheNameConstant.currentUser);
    }
}
