package com.mapfre.tron.api.cmn.thp.cst.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OTpdCsgSPK implements NewtronPK {
	

	private BigDecimal cmpVal;
	private String usrVal;
	private String lngVal;
	private String csgTypVal;

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
		
		if(csgTypVal!=null) {
		    map.put("csgTypVal", csgTypVal);
		}
		
		
		return map;

	}

}
