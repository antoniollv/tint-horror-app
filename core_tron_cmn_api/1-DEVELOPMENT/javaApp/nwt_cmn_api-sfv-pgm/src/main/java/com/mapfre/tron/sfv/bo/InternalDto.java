package com.mapfre.tron.sfv.bo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Internal objeto to pass data through chained SFV Beans.
 */
@Component @RequestScope
public class InternalDto {

	private Map<String, Object> data = new HashMap<>();
	
	public void set(String k, Object v) {
		data.put(k, v);
	}
	
	public Object get(String k) {
		return data.get(k);
	}
	
	public boolean contains(String k) {
		return data.containsKey(k);
	}
}
