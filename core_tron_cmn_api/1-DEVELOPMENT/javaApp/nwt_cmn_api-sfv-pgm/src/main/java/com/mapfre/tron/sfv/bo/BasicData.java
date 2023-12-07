package com.mapfre.tron.sfv.bo;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BasicData {
	private BigDecimal cmpVal;
	private String usrVal;
	private String lngVal;
	private String processTypeId;
	private String lobVal;
	private String pmsVal;
}
