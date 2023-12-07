package com.mapfre.tron.api.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author ARQUITECTURA
 *
 *         Class to schedule cache cleaning.
 * 
 */
@Slf4j
@Component
public class CacheScheduling {

	@Autowired
	ICachingService cachingService;

	@Scheduled(cron = "${cache.clean.cron.expression:0 0 2 29 2 ?}")
	private void evictAllCachesWithCron() {
		try {
			cachingService.evictAllCaches();
		} catch (Exception e) {
			log.warn("Scheduled Cache Eviction Failed", e);

		}
		log.warn("Scheduled Cache Eviction Succesful");
	}
}