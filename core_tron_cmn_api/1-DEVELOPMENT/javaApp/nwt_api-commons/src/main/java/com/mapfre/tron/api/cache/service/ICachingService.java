package com.mapfre.tron.api.cache.service;

import java.util.Collection;

public interface ICachingService {

    /**
     * Cleans a value of a cache from its key
     * 
     * @param cacheName
     * @param cacheKey
     */
    void evictSingleCacheValue(String cacheName, String cacheKey);

    /**
     * Cleans all values of a cache
     * 
     * @param cacheName
     */
    void evictAllCacheValues(String cacheName);

    /**
     * Cleans all caches
     */
    void evictAllCaches();

    /**
     * Returns a list of all cache names
     * 
     * @return
     */
    Collection<String> getAllCacheNames();

}