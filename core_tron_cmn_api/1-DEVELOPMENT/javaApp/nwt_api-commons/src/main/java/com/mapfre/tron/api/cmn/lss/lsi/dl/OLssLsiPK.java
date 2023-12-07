package com.mapfre.tron.api.cmn.lss.lsi.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.lss.lsi.bo.OLssLsiS;

import lombok.Builder;
import lombok.Data;


@Data 
@Builder
public class OLssLsiPK implements NewtronPK {
    
    	private BigDecimal cmpVal;
	private String lngVal;
	private BigDecimal lssRsnVal;

	public static final OLssLsiPK get(OLssLsiS s) {
	    	return OLssLsiPK.builder().lssRsnVal(s.getLssRsnVal()).build();
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
			
		if(lssRsnVal!=null) {
		    map.put("lssRsnVal", lssRsnVal);
		}
		
		return map;
	}

}
