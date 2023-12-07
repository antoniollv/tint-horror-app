package com.mapfre.tron.api.cmn.tpd.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OTpdAgtSPK implements NewtronPK {
	

	private BigDecimal cmpVal;
	private String usrVal;
	private String lngVal;
	private String agnTypVal;

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
		
		if(agnTypVal!=null) {
		    map.put("agnTypVal", agnTypVal);
		}
		
		
		return map;

	}

}
