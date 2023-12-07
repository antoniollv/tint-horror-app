package com.mapfre.tron.api.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Controller
@Api(tags={ "Cache",})
public class CacheApiController implements CacheApi {

    @Autowired
    ICachingService cachingService;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResponseEntity<Void> getCacheClear() {
        log.info("The getCacheClear rest operation had been called...");

        log.debug("Tronweb business logic implementation rmv have been called...");

        try {
            cachingService.evictAllCaches();
            return new ResponseEntity("Operation successfully done!", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error cleaning all caches", e);
            return new ResponseEntity("Error during chache deletion!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
