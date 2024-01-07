package com.xuanluan.mc.sdk.authenticate.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@PropertySource(value = {"classpath:/cache.properties"})
public class CacheUserConfig {

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
