package kiwi.blog.common.config;

import kiwi.blog.common.model.enums.CacheName;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(getCacheNames());

        return cacheManager;
    }

    private List<Cache> getCacheNames() {

        return Arrays.stream(CacheName.values())
                .map(cacheName -> new ConcurrentMapCache(cacheName.name()))
                .collect(Collectors.toList());
    }
}