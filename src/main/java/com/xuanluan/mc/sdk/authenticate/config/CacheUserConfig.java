package com.xuanluan.mc.sdk.authenticate.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.xuanluan.mc.sdk.config.BaseCacheConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class CacheUserConfig extends BaseCacheConfig {

    @Value("${cache.user.expire_time:120}")
    private int expireTime;
    @Value("${cache.user.period:MINUTES}")
    private String period;
    @Value("${cache.user.maxsize:10000}")
    private int maxSize;

    @Bean
    public CacheManager currentUserCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(expireTime, TimeUnit.valueOf(period.toUpperCase()))
                        .maximumSize(maxSize)
        );
        return caffeineCacheManager;
    }
}
