package com.mapfre.tron.sfv.pgm.beans.impl.fn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mapfre.tron.sfv.bo.IGeneralSfvFunction;

@Component("GRCSfvFnConcat") @SuppressWarnings("unchecked")
public class GeneralSfvFnConcat implements IGeneralSfvFunction {
	
	@Override
	public String apply(String v, Object params, Map<String, String> values) {
		if (StringUtils.isNotEmpty(v)) {
			List<String> fmts = (List<String>)params;
			List<String> data = new ArrayList<>();
			data.add(v);
			fmts.stream().forEach(x -> data.add(values.get(x)));
			return data.stream().collect(Collectors.joining(" "));
		}
		return v;
	}

}
