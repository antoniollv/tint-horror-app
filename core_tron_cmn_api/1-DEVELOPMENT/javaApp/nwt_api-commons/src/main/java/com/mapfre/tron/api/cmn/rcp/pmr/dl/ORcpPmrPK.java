package com.mapfre.tron.api.cmn.rcp.pmr.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.rcp.pmr.bo.ORcpPmrS;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class ORcpPmrPK implements NewtronPK {
    
	private BigDecimal cmpVal;
	private String lngVal;
	private String typVal;
	private BigDecimal crnVal;
	
	public static final ORcpPmrPK get(ORcpPmrS s) {
	    	return ORcpPmrPK.builder().typVal(s.getRcpStsTypVal()).crnVal(s.getCrnVal()).cmpVal(s.getCmpVal()).build();
	    }
	

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		if(cmpVal!=null) {
		    map.put("cmpVal", cmpVal);
		}
		
		if(lngVal!=null) {
		    map.put("lngVal", lngVal);
		}
			
		if(typVal!=null) {
		    map.put("typVal", typVal);
		}
		
		if(crnVal!=null) {
		    map.put("crnVal", crnVal);
		}
		
		return map;
	}

}
