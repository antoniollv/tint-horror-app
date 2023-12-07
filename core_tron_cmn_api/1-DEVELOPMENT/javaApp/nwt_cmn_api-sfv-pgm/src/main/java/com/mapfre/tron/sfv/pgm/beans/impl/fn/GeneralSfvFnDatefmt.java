package com.mapfre.tron.sfv.pgm.beans.impl.fn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mapfre.tron.sfv.bo.IGeneralSfvFunction;

@Component("GRCSfvFnDatefmt") @SuppressWarnings("unchecked")
public class GeneralSfvFnDatefmt implements IGeneralSfvFunction {
	
	@Override
	public String apply(String v, Object params, Map<String, String> values) {
		if (StringUtils.isNotEmpty(v)) {
			List<String> fmts = (List<String>)params;
			SimpleDateFormat sdf1 = new SimpleDateFormat(fmts.get(0));
			SimpleDateFormat sdf2 = new SimpleDateFormat(fmts.get(1));
			
			try {
				return sdf2.format(sdf1.parse(v));
			} catch(ParseException e) {
				return v;
			}
		}
		return v;
	}

}
