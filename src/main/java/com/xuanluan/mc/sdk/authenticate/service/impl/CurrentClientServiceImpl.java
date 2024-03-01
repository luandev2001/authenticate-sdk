package com.xuanluan.mc.sdk.authenticate.service.impl;

import com.xuanluan.mc.sdk.authenticate.service.constant.CacheNameConstant;
import com.xuanluan.mc.sdk.service.tenant.ITenantProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;
import com.xuanluan.mc.sdk.authenticate.domain.model.CurrentClient;
import com.xuanluan.mc.sdk.authenticate.service.ICurrentClientService;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

@RequiredArgsConstructor
@CacheConfig(cacheNames = CacheNameConstant.currentClient)
public class CurrentClientServiceImpl implements ICurrentClientService {
    private final ITenantProvider tenantProvider;
    private final Set<String> schemas = new HashSet<>();

    @Cacheable(key = "#id")
    @Override
    public CurrentClient get(String id, Supplier<CurrentClient> clientSupplier) {
        CurrentClient currentClient = clientSupplier.get();
        Assert.notNull(currentClient, "client must not null");
        //not exists schema => migrate schema and add to array schema
        if (!schemas.contains(id)) {
            tenantProvider.create(id);
            schemas.add(id);
        }
        return currentClient;
    }
}
