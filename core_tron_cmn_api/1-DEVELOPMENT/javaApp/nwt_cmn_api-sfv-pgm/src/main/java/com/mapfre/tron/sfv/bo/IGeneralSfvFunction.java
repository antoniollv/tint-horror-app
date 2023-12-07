package com.mapfre.tron.sfv.bo;

import java.util.Map;

public interface IGeneralSfvFunction {
	String apply(String v, Object params, Map<String, String> values);
}
