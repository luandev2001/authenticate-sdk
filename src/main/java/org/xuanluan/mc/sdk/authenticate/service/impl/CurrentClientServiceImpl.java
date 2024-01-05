package org.xuanluan.mc.sdk.authenticate.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;
import org.xuanluan.mc.sdk.authenticate.domain.model.CurrentClient;
import org.xuanluan.mc.sdk.authenticate.service.ICurrentClientService;
import org.xuanluan.mc.sdk.authenticate.service.constant.CacheNameConstant;

import java.util.function.Supplier;

@CacheConfig(cacheNames = CacheNameConstant.currentClient)
public class CurrentClientServiceImpl implements ICurrentClientService {

    @Cacheable(key = "#id")
    @Override
    public CurrentClient get(String id, Supplier<CurrentClient> clientSupplier) {
        CurrentClient currentClient = clientSupplier.get();
        Assert.notNull(currentClient, "client must not null");
        return currentClient;
    }
}
