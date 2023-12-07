package com.mapfre.tron.sfv.bo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OFlrDfn {
	private BigDecimal cmpVal; 
	private BigDecimal frlVal;
	private String frlNam;
	private String frlShtNam;
	private String pgmNam;
}
