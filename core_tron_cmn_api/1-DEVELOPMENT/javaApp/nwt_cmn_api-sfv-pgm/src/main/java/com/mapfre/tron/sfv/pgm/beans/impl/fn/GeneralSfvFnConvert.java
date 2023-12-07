package com.mapfre.tron.sfv.pgm.beans.impl.fn;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mapfre.tron.sfv.bo.IGeneralSfvFunction;

@Component("GRCSfvFnConvert") @SuppressWarnings("unchecked")
public class GeneralSfvFnConvert implements IGeneralSfvFunction {
	
	@Override
	public String apply(String v, Object params, Map<String, String> values) {
		if (StringUtils.isNotEmpty(v)) {
			Map<String, String> fmts = (Map<String, String>)params;
			if (fmts.containsKey(v)) {
				return fmts.get(v);
			}
		}
		return v;
	}

}
