package com.mapfre.tron.api.cache.service;

import java.util.Collection;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * Class for managing cache cleaning
 * 
 * @author ARQUITECTURA
 *
 */
@Component("CachingServiceAPIsImpl")
public class CachingServiceImpl implements ICachingService {

    @Autowired
    CacheManager cacheManager;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.mapfre.nwt.cache.ICachingService#evictSingleCacheValue(java.lang.String,
     * java.lang.String)
     */
    @Override
    public void evictSingleCacheValue(String cacheName, String cacheKey) {
	cacheManager.getCache(cacheName).evict(cacheKey);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.mapfre.nwt.cache.ICachingService#evictAllCacheValues(java.lang.String)
     */
    @Override
    public void evictAllCacheValues(String cacheName) {
	cacheManager.getCache(cacheName).clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.mapfre.nwt.cache.ICachingService#evictAllCaches()
     */
    @Override
    public void evictAllCaches() {
	cacheManager.getCacheNames().parallelStream().forEach(new Consumer<String>() {
	    @Override
	    public void accept(String cacheName) {
		cacheManager.getCache(cacheName).clear();
	    }
	});
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.mapfre.nwt.cache.ICachingService#getAllCacheNames()
     */
    @Override
    public Collection<String> getAllCacheNames() {
	return cacheManager.getCacheNames();
    }

}