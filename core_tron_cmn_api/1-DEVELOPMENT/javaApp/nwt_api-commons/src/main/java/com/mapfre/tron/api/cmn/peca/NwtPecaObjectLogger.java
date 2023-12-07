package com.mapfre.tron.api.cmn.peca;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


import lombok.extern.slf4j.Slf4j;

/**
 * Auxiliary logger object.
 * Allows to delegate serialization to asynchronous logback logger.
 */
@Slf4j
public class NwtPecaObjectLogger {
    /** PECA configuration. */
	private static NwtPecaAspectConfig config;
	/** Jackson serializer. */
	private static ObjectMapper om;
	/** GSON serializer. */
	private static Gson gson;
	/** Server. */
	private static String server;
	/** User */
	private String user;
	
	/**
	 * Initialize common elements.
	 * @param oconfig PECA configuration
	 * @param oom Jackson serializer
	 * @param ogson GSON serializer
	 * @param oserver Server
	 */
	public static void init(NwtPecaAspectConfig oconfig, ObjectMapper oom, Gson ogson, String oserver) {
		config = oconfig;
		om = oom;
		gson = ogson;
		server = oserver;
	}
	
	/** input object. */
	private Object[] in;
	/** output object. */
	private Object result;
	/** error object. */
	private Object error;
	/** operation executed. */
	private String op;
	
	/**
	 * Constructor.
	 * @param op operation executed
	 * @param in input object
	 * @param result output object
	 * @param error error object
	 */
	public NwtPecaObjectLogger(String op, Object[] in, Object result, Object error, String user) {
		this.op = op;
		this.in = in;
		this.result = result;
		this.error = error;
		this.user = user;
	}
	
	/**
	 * Serialization lo PECA log.
	 */
	public String toString() {
		String clientIP = getClientIp(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
		//getUser();
		String inStr = serialize(in);
		String out = error != null ? serialize(error) : serializeOut(result);
		
		String trace = StringUtils.replace(config.getPattern(), "${IP}", clientIP);
		trace = StringUtils.replace(trace, "${USR}", this.user);
		trace = StringUtils.replace(trace, "${SRV}", server);
		trace = StringUtils.replace(trace, "${OP}", this.op);
		trace = StringUtils.replace(trace, "${IN}", inStr);
		trace = StringUtils.replace(trace, "${OUT}", out);
		trace = StringUtils.replace(trace, "${ST}", error == null ? "OK": "ERROR");
		
		return trace;
	}
	
	/**
	 * Serializes object as string.
	 * @param o object to serialize
	 * @return result string
	 */
	private String serialize(Object o) {
		try {
			if (config.isGson()) {
				return gson.toJson(o);
			}
			return om.writeValueAsString(o);
		} catch (IOException e) {
			log.error("PECA Error serializing", e);
		}
		return "";
	}
	
	/**
	 * Serializes out object as string.
	 * @param o object to serialize
	 * @return result string
	 */
	private String serializeOut(Object o) {
		if (o instanceof ResponseEntity<?> && !config.isResponse()) {
			return serialize(((ResponseEntity<?>)o).getBody());
		} else {
			return serialize(o);
		}
	}
	
	/** Headers to extract client-ip. */
	private static final String[] HEADERS_LIST = {
			"X-Forwarded-For",
			"Proxy-Client-IP",
			"WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR",
			"HTTP_X_FORWARDED",
			"HTTP_X_CLUSTER_CLIENT_IP",
			"HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR",
			"HTTP_FORWARDED",
			"HTTP_VIA",
			"REMOTE_ADDR"
	};

	/**
	 * Get client-ip from headers.
	 * @param request call request
	 * @return client ip
	 */
	private String getClientIp(HttpServletRequest request) {
		for (String header : HEADERS_LIST) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}
	
	


}
