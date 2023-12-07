package com.mapfre.tron.api.cmn.contributor.bl;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TronNamingBeanInfo {

	private String className;
	private List<String> errors = new ArrayList<>();

}
