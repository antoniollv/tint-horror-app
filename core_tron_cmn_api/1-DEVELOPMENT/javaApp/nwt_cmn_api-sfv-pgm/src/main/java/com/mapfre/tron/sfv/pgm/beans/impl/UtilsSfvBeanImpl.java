package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The Utils SfvBean implementation.
 * General utilities to map data and put from parametrization
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("UtilsSfv") @SuppressWarnings("unchecked")
public class UtilsSfvBeanImpl extends SfvWithConditionalBeanBase {
	
	@Override
	public SfvOut logic(SfvIn in, BigDecimal cmpVal, String usrVal, String lngVal, Map<String, Object> params) {
		if (log.isInfoEnabled()) {
            log.info("UtilsSfvBeanImpl.execute start execute operation...");
        }
		
		SfvOut lvSfvOut = sfvMapper.map(in);
		
		/*
		  Mapeo de variables de entrada a la salida map:
		*/
		if (params.containsKey("map")) {
		  Map<String, String> mapDef = (Map<String, String>)params.get("map");
		  for (Map.Entry<String, String> e : mapDef.entrySet()) {
		    lvSfvOut.getParameters().put(e.getKey(), lvSfvOut.getParameters().get(e.getValue()));
		  }
		}
		
		/*
		  Carga de variables de parametrización a la salida set
		*/
		if (params.containsKey("set")) {
		  Map<String, String> defaults = new HashMap<>();
		  defaults.put("${USR_VAL}", usrVal);
		  defaults.put("${LNG_VAL}", lngVal);
		  defaults.put("${CMP_VAL}", cmpVal.toPlainString());

		  Map<String, String> mapDef = (Map<String, String>)params.get("set");
		  for (Map.Entry<String, String> e : mapDef.entrySet()) {
			if (defaults.containsKey(e.getValue())) {
			  lvSfvOut.getParameters().put(e.getKey(), defaults.get(e.getValue()));
			} else {
		      lvSfvOut.getParameters().put(e.getKey(), e.getValue());
			}
		  }
		}
		
		return lvSfvOut;
	}
}
