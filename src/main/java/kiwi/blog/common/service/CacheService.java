package kiwi.blog.common.service;

import kiwi.blog.common.model.enums.CacheName;
import kiwi.blog.common.utils.JacksonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    public String get(CacheName cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName.name());
        assert cache != null;

        return cache.get(key, String.class);
    }

//  public <T> String get(CacheName cacheName, String key, Class<T> clazz) {
//    Cache cache = cacheManager.getCache(cacheName.name());
//    assert cache != null;
//
//    return JacksonUtils.toForceModel()cache.get(key, String.class);
//  }

    public void put(CacheName cacheName, String key, String value) {
        Cache cache = cacheManager.getCache(cacheName.name());
        assert cache != null;
        cache.put(key, value);
    }

    public void put(CacheName cacheName, String key, Object value) {
        Cache cache = cacheManager.getCache(cacheName.name());
        assert cache != null;
        cache.put(key, JacksonUtils.toForceJson(value));
    }

    public void evict(CacheName cacheName) {
        Cache cache = cacheManager.getCache(cacheName.name());
        assert cache != null;
        cache.clear();
    }

}
