package com.mapfre.tron.api.cache.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Api(value = "cacheTypes")
@RequestMapping(value = "/newtron/api/common")

public interface CacheApi {

    Logger log = LoggerFactory.getLogger(CacheApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Query cache clear", nickname = "getCacheClear", notes = "Query cache clear. It will clear the cache.", authorizations = {
            @Authorization(value = "basicAuth") }, tags = { "Cache", })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Operation succesfully done!"),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal error", response = Error.class) })
    @GetMapping(value = "/cacheClear", produces = { "application/json" })
    default ResponseEntity<Void> getCacheClear() {
        if (!getObjectMapper().isPresent() || !getAcceptHeader().isPresent()) {
            log.warn(
                    "ObjectMapper or HttpServletRequest not configured in default cacheApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
