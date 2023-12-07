package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mapfre.tron.sfv.bo.OCmnWbsDfnS;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The GeneralSoapCallSfv SfvBean implementation.
 * General SOAP calls
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("GeneralSoapCallSfv") @SuppressWarnings("unchecked")
public class GeneralSoapCallSfvBeanImpl extends GeneralHttpCallSfvBeanBase {
	
	@Override
	public SfvOut logic(SfvIn in, BigDecimal cmpVal, String usrVal, String lngVal, Map<String, Object> params) {
		if (log.isInfoEnabled()) {
            log.info("GeneralSoapCallSfvBeanImpl.execute start execute operation...");
        }
        
        SfvOut lvSfvOut = sfvMapper.map(in);
        OCmnWbsDfnS out = getCallData(cmpVal, params);

        RestTemplate rest = getTemplate(out);
		
		HttpHeaders headers = getAuth(out);
		
		Map<String, String> context = getContext(cmpVal, usrVal, lngVal);
		Map<String, Object> svcInput = new HashMap<>(in.getParameters());
		addHeaders(headers, params, svcInput, context);
		if (!headers.containsKey("Content-Type")) {
			headers.add("Content-Type", "application/soap+xml; charset=utf-8");
		}

		UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(out.getUrlWbsTxtVal());
		String url = uri.build(false).toUriString();
		
		// Create the request
		HttpEntity<?> request = getEntity(svcInput, params, context, "POST", headers, true);
		boolean skipError = params.containsKey("skipError") && Boolean.parseBoolean(getString(params.get("skipError")));
        
		try {
			log.debug(url);
			log.debug(request.toString());
			if (params.containsKey("log")) {
        		lvSfvOut.getParameters().put("REQUEST", request);
        	}
			ResponseEntity<String> res = rest.exchange(url, HttpMethod.POST, request, String.class);

			// Translate output to SfvOut
			if (res.getBody() != null) {
				// Parse XML answer
				XmlMapper xmlMapper = new XmlMapper();
		    	Map<String, Object> value = xmlMapper.readValue(res.getBody(), new TypeReference<Map<String, Object>>() {});
		    	Map<String, Object> bodyRes = (Map<String, Object>)value.get("Body");
		    	innerData.set("GeneralRestCallSfv", bodyRes);
				
				processResult(params, lvSfvOut, skipError, bodyRes);
			}
		} catch(Exception e) {
			processException(lvSfvOut, skipError, e);
		}
        
        return lvSfvOut;
	}
}
