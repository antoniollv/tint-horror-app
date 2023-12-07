package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mapfre.tron.sfv.bo.OCmnWbsDfnS;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The GeneralRestCallSfv SfvBean implementation.
 * general REST calls
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("GeneralRestCallSfv") @SuppressWarnings("unchecked")
public class GeneralRestCallSfvBeanImpl extends GeneralHttpCallSfvBeanBase {
	
	@Override
	public SfvOut logic(SfvIn in, BigDecimal cmpVal, String usrVal, String lngVal, Map<String, Object> params) {
		if (log.isInfoEnabled()) {
            log.info("GeneralRestCallSfvBeanImpl.execute start execute operation...");
        }
        
        SfvOut lvSfvOut = sfvMapper.map(in);
        Map<String, String> context = getContext(cmpVal, usrVal, lngVal);

        boolean skipError = params.containsKey("skipError") && Boolean.parseBoolean(getString(params.get("skipError")));
        OCmnWbsDfnS out = getCallData(cmpVal, params);

        RestTemplate rest = getTemplate(out);
		
		Map<String, Object> svcInput = new HashMap<>(in.getParameters());
		
		HttpHeaders headers = getAuth(out);
		headers.setContentType(MediaType.APPLICATION_JSON);
		addHeaders(headers, params, svcInput, context);

		UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(out.getUrlWbsTxtVal());
		processQueryParams(params, svcInput, uri, context);
		String url = uri.build(false).toUriString();
		
		// Create the request
		HttpEntity<?> request = getEntity(svcInput, params, context, out.getMthTypVal(), headers, false);
		
		try {
			log.debug(url);
			log.debug(request.toString());
			HttpMethod m = HttpMethod.resolve(out.getMthTypVal());
			if (params.containsKey("log")) {
        		lvSfvOut.getParameters().put("REQUEST", request);
        	}
			ResponseEntity<Map<String, Object>> res = rest.exchange(url,
					m != null ? m : HttpMethod.POST,
					request,
					new ParameterizedTypeReference<Map<String, Object>>() {});

			// Translate output to SfvOut
			if (res.getBody() != null) {
				innerData.set("GeneralRestCallSfv", res.getBody());
				processResult(params, lvSfvOut, skipError, res.getBody());
			}
		} catch(Exception e) {
			processException(lvSfvOut, skipError, e);
		}
        
        return lvSfvOut;
	}
	
	/**
	 * Precess query parameters
	 * @param params parametrization
	 * @param svcInput service input
	 * @param uri uri to complete
	 */
	private void processQueryParams(Map<String, Object> params, Map<String, Object> in, UriComponentsBuilder uri, Map<String, String> context) {
		if (params.containsKey("queryParams")) {
			log.debug("Creating Query parameters ..." );
			Map<String, Map<String, String>> queryParamsDef = (Map<String, Map<String, String>>)params.get("queryParams");
			queryParamsDef.entrySet().stream().forEach(e -> uri.queryParam(e.getKey(), getValue(in, e.getValue(), true, context)));
	    }
	}
}
