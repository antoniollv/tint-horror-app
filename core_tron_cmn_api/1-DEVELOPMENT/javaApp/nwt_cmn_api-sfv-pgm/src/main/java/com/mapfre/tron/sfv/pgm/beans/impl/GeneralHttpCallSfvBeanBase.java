package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.mapfre.tron.sfv.bo.IGeneralSfvFunction;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.OCmnWbsDfnS;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.dl.IDlCmnWbsDfnDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * The HTTP common functions SfvBean implementation.
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@SuppressWarnings("unchecked")
public abstract class GeneralHttpCallSfvBeanBase extends SfvWithConditionalBeanBase {
	
	private static final String ZERO_CONFIG = "zeroConfig";
	@Autowired
    protected IDlCmnWbsDfnDAO dao;
	@Autowired
    protected ApplicationContext ctx;
	@Autowired
	protected Environment env;

	/**
	 * Calculate errors output.
	 * @param params parametrization
	 * @param lvSfvOut output
	 * @param skipError skip error indicator
	 * @param result service call result
	 */
	protected void calculateErrors(Map<String, Object> params, SfvOut lvSfvOut, boolean skipError, Map<String, String> result) {
		if (params.containsKey("mapErrors") && !skipError) {
			log.debug("Mapping errors ...");
			List<Map<String, String>> mapErrosDef = (List<Map<String, String>>)params.get("mapErrors");
			mapErrosDef.stream().forEach(e -> {
				if (result.containsKey(e.get("code")) && StringUtils.isNotEmpty(result.get(e.get("code"))) &&
						(!e.containsKey("if") || Arrays.asList(e.get("if").split(",")).contains(result.get(e.get("code")))) &&
						(!e.containsKey("noif") || !Arrays.asList(e.get("noif").split(",")).contains(result.get(e.get("code"))))) {
					Message lvMessagesItem = getMessage(result.get(e.get("message")), 3, Integer.valueOf(result.get(e.get("code"))), null);
					lvSfvOut.addMessagesItem(lvMessagesItem);
				}
			});
		}
	}

	/**
	 * Calculate output.
	 * @param params parametrization
	 * @param lvSfvOut output
	 * @param result service call result
	 */
	protected void calculateReturn(Map<String, Object> params, SfvOut lvSfvOut, Map<String, String> result) {
		if (params.containsKey("map")) {
			log.debug("Mapping output ...");
			Map<String, Map<String, Object>> mapDef = (Map<String, Map<String, Object>>)params.get("map");
			mapDef.entrySet().stream().forEach(e -> {
				String origin = getString(e.getValue().get("vrb"));
				String dest = e.getKey();
				String apply = getString(e.getValue().get("apply"));
				Object paramsApply = e.getValue().get("params");
				if (StringUtils.isEmpty(apply)) {
					lvSfvOut.getParameters().put(dest, result.get(origin));
				} else {
					IGeneralSfvFunction f = ctx.getBean(apply, IGeneralSfvFunction.class);
					lvSfvOut.getParameters().put(dest, f.apply(result.get(origin), paramsApply, result));
				}
			});
		} else if (params.containsKey("mapAll") && Boolean.parseBoolean(params.get("mapAll").toString())) {
			log.debug("All service out to output");
			lvSfvOut.getParameters().putAll(result);
		}
	}

	/**
	 * Generate entity.
	 * @param in input data
	 * @param params parametrization data
	 * @param method http mnethod
	 * @param headers request headers
	 * @return entity
	 */
	@SuppressWarnings("rawtypes")
	protected HttpEntity getEntity(Map<String, Object> in, Map<String, Object> params, Map<String, String> context, String method, HttpHeaders headers, boolean xml) {
		log.debug("Creating BODY ...");
		HttpEntity<?> request;

		if ("GET".equals(method)) {
			request = new HttpEntity<>(headers);
		} else if (params.containsKey("bodyDef")) {
			String tpl = params.get("bodyDef").toString();
			Map<String, Object> valuesMap = new HashMap<>(in);
			valuesMap.putAll(context.entrySet().stream().collect(Collectors.toMap(
					k -> k.getKey().substring(2, k.getKey().length() - 1), 
					v -> xml ? StringEscapeUtils.escapeXml11(v.getValue()) : StringEscapeUtils.escapeJson(v.getValue())
			)));
			
			processConcats(tpl, valuesMap);
			
	    	StringSubstitutor sub = new StringSubstitutor(valuesMap);
	    	request = new HttpEntity<>(sub.replace(tpl), headers);
	    } else {
			// post/put methods can include a body
			if (params.containsKey("body")) {
				List<String> toBody = (List<String>)params.get("body");
				in.keySet().retainAll(toBody);
			}
			request = new HttpEntity<>(in, headers);
		}
		return request;
	}
	
	private void processConcats(String tpl, Map<String, Object> valuesMap) {
		Pattern p = Pattern.compile("\\$\\{concat\\((.+?)\\)\\}");
        Matcher m = p.matcher(tpl);
        while (m.find()) {
        	String cc = m.group(0);
        	String cct = cc.substring(2, cc.length() - 1);
        	String ccd = cc.substring(9, cc.length() - 2);
        	String[] items = ccd.split(",");
        	List<String> ccs = new ArrayList<>();
        	for (String item : items) {
        		String it = item.trim();
        		if (valuesMap.containsKey(it)) {
        			String v = getString(valuesMap.get(it));
        			if (StringUtils.isNotEmpty(v)) {
        				ccs.add(v);
        			}
        		}
        	}
        	String ff = ccs.stream().collect(Collectors.joining(" "));
        	valuesMap.put(cct, ff);
        }
	}
	
	/**
	 * Create HttpHeaders for authentication.
	 * @param out data
	 * @return headers
	 */
	protected HttpHeaders getAuth(OCmnWbsDfnS out) {
		log.debug("Creating AUTHORIZATION ...");
		HttpHeaders headers = new HttpHeaders();
		if (StringUtils.isNotEmpty(out.getWbsUsrVal()) && StringUtils.isNotEmpty(out.getWbsPswTxtVal())) {
			String auth = out.getWbsUsrVal() + ":" + out.getWbsPswTxtVal();
			String authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.US_ASCII));
			headers.set(HttpHeaders.AUTHORIZATION, authHeader);
		}
		return headers;
	}
	
	/**
	 * Add HttpHeaders for common parameters.
	 * @param params parametrization
	 * @paran in input data
	 */
	protected void addHeaders(HttpHeaders headers, Map<String, Object> params, Map<String, Object> in, Map<String, String> context) {
		log.debug("Creating HttpHeaders ...");
		if (params.containsKey("headers")) {
			Map<String, Map<String, String>> headersDef = (Map<String, Map<String, String>>)params.get("headers");
			headersDef.entrySet().stream().forEach(e -> headers.add(e.getKey(), getValue(in, e.getValue(), true, context)));
	    }
	}
	
	/**
	 * Get value from a fix falue, input service or context.
	 * @param in input service
	 * @param param parametrization
	 * @param remove remove indicator
	 * @param context contect (user, language, ...)
	 * @return value
	 */
	protected String getValue(Map<String, Object> in, Map<String, String> param, boolean remove, Map<String, String> context) {
		if (param.containsKey("value")) {
			return param.get("value");
		} else if (param.containsKey("vrb")) {
			String vrb = param.get("vrb");
			String val = context.containsKey(vrb) ? context.get(vrb) : getString(in.get(vrb));
			if (remove) {
				in.remove(vrb);
			}
			return val;
		}
		return "";
	}
	
	/**
	 * Flat return map.
	 * @param prefix property prefix
	 * @param o object
	 * @param result map flattered
	 */
	protected void flatMap(String prefix, Object o, Map<String, String> result) {
    	if (o instanceof Map) {
    		Map<?, ?> map = (Map<?, ?>)o;
    		final String myprefix = StringUtils.isEmpty(prefix) ? "" : prefix + ".";
    		map.entrySet().stream().forEach(e -> flatMap(myprefix + e.getKey(), e.getValue(), result));
    	} else if (o instanceof List) {
    		List<?> list = (List<?>)o;
    		for (int idx = 0; idx < list.size(); idx++) {
    			final String myprefix = (StringUtils.isEmpty(prefix) ? "" : prefix + ".") + idx;
    			flatMap(myprefix, list.get(idx), result);
    		}
    	} else {
    		result.put(prefix, getString(o));
    	}
    }
	
	protected RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	/**
	 * Get data to call from params or BBDD.
	 * @param cmpVal company
	 * @param params parametrization
	 * @return call data
	 */
	protected OCmnWbsDfnS getCallData(BigDecimal cmpVal, Map<String, Object> params) {
		OCmnWbsDfnS out = new OCmnWbsDfnS();
        if (params.containsKey("wbsCodVal") && params.containsKey("wbsSbdVal")) {
        	OCmnWbsDfnS oin = new OCmnWbsDfnS();
        	oin.setCmpVal(cmpVal);
        	oin.setWsCodVal(getString(params.get("wbsCodVal")));
        	oin.setWsSbdVal(getString(params.get("wbsSbdVal")));
        	out = dao.get(oin);
        }
        
        if (StringUtils.isEmpty(out.getUrlWbsTxtVal()) && params.containsKey(ZERO_CONFIG)) {
        	out.setUrlWbsTxtVal(env.getProperty(getString(params.get(ZERO_CONFIG)) + ".url"));
        	out.setWbsUsrVal(env.getProperty(getString(params.get(ZERO_CONFIG)) + ".user"));
        	out.setWbsPswTxtVal(env.getProperty(getString(params.get(ZERO_CONFIG)) + ".pass"));
        	out.setTmtVal(Long.valueOf(env.getProperty(getString(params.get(ZERO_CONFIG)) + ".timeout", "5")));
        	out.setMthTypVal(env.getProperty(getString(params.get(ZERO_CONFIG)) + ".method", "POST"));
        }
        
        if (StringUtils.isEmpty(out.getUrlWbsTxtVal())) {
        	out.setUrlWbsTxtVal(getString(params.get("url")));
        	out.setWbsUsrVal(getString(params.get("user")));
        	out.setWbsPswTxtVal(getString(params.get("pass")));
        	out.setTmtVal(Long.valueOf(getString(params.get("timeout"))));
        	out.setMthTypVal(getString(params.get("method")));
        }
        out.setTmtVal(out.getTmtVal() * 1000);
		return out;
	}
	
	protected Map<String, String> getContext(BigDecimal cmpVal, String usrVal, String lngVal) {
		Map<String, String> context = new HashMap<>();
        context.put("${CMP_VAL}", cmpVal.toString());
        context.put("${USR_VAL}", usrVal);
        context.put("${LNG_VAL}", lngVal);
		return context;
	}
	
	protected RestTemplate getTemplate(OCmnWbsDfnS out) {
		RestTemplate rest = getRestTemplate();
		SimpleClientHttpRequestFactory fact = (SimpleClientHttpRequestFactory)rest.getRequestFactory();
		fact.setReadTimeout(out.getTmtVal().intValue());
		fact.setConnectTimeout(out.getTmtVal().intValue());
		return rest;
	}
	
	protected void processResult(Map<String, Object> params, SfvOut lvSfvOut, boolean skipError, Map<String, Object> bodyRes) {
		Map<String, String> serviceCallResult = new HashMap<>();
		flatMap("", bodyRes, serviceCallResult);
		
		calculateReturn(params, lvSfvOut, serviceCallResult);

		calculateErrors(params, lvSfvOut, skipError, serviceCallResult);
	}
	
	protected void processException(SfvOut lvSfvOut, boolean skipError, Exception e) {
		log.debug(e.toString());
		if (!skipError) {
			Message lvMessagesItem = null;
			if (e instanceof RestClientResponseException) {
				RestClientResponseException ee = (RestClientResponseException)e;
				lvMessagesItem = getMessage(ee.getResponseBodyAsString(), 3, ee.getRawStatusCode(), null);
			} else {
				lvMessagesItem = getMessage(e.getMessage(), 3, 500, null);
			}
			lvSfvOut.addMessagesItem(lvMessagesItem);
		}
	}
}
