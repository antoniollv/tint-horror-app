package com.mapfre.tron.sfv.pgm.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

/**
 * The sfv mapper.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 may 2023 - 7:53:24
 *
 */
@Mapper(componentModel = "spring")
public interface SfvMapper {
	
	static final ObjectMapper om = new ObjectMapper();

    @Mapping(target = "navigation", ignore = true)
    SfvOut map(SfvIn in);

    @Mapping(target = "navigation", ignore = true)
    @InheritInverseConfiguration
    SfvIn map(SfvOut out);

    @Named("jsonToObject")
    default Map<String, Object> jsonToObject(String json) {
    	Map<String, Object> empty = new HashMap<>();

    	if (StringUtils.isEmpty(json)) {
    	  return empty;
    	}
    	
    	try {
    	  return om.readValue(json, new TypeReference<Map<String, Object>>() {});
    	} catch (IOException e) {
    	  return empty;
    	}
    }
}
