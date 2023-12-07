package com.mapfre.tron.api.cmn.rcp.pmr.dl;

import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class ORcdTsdSPK implements NewtronPK {
    
	private String rcpStsTypVal;
	
	public static final ORcdTsdSPK get(String rcpStsTypVal) {
	    	return ORcdTsdSPK.builder().rcpStsTypVal(rcpStsTypVal).build();
	    }
	

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();

		if(rcpStsTypVal!=null) {
		    map.put("rcpStsTypVal", rcpStsTypVal);
		}
		
		return map;
	}

}
