package com.mapfre.tron.api.cmn.lss.fli.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.lss.fli.bo.OLssFliS;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class OLssFliPK implements NewtronPK {
    
	private BigDecimal cmpVal;
	private String lngVal;
	private String filTypVal;

	public static final OLssFliPK get(OLssFliS s) {
	    	return OLssFliPK.builder().filTypVal(s.getFilTypVal()).build();
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
			
		if(filTypVal!=null) {
		    map.put("filTypVal", filTypVal);
		}
		
		return map;
	}

}
