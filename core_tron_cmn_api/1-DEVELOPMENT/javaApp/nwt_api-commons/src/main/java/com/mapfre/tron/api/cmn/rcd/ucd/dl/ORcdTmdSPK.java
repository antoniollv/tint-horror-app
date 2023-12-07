package com.mapfre.tron.api.cmn.rcd.ucd.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ORcdTmdSPK implements NewtronPK {
	

	private BigDecimal cmpVal;
	private String usrVal;
	private String lngVal;
	private String mnrTypVal;

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		if(cmpVal!=null) {
		    map.put("cmpVal", cmpVal);
		}
		
		if(lngVal!=null) {
		    map.put("lngVal", lngVal);
		}
			
		if(usrVal!=null) {
		    map.put("usrVal", usrVal);
		}
		
		if(mnrTypVal!=null) {
		    map.put("mnrTypVal", mnrTypVal);
		}
		
		
		return map;

	}

}
