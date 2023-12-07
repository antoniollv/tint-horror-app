package com.mapfre.tron.api.cmn.grs.zot.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;

import lombok.Builder;
import lombok.Data;


@Data 
@Builder
public class OgrsZotPK implements NewtronPK {
    
    	private String cnyVal;
	private BigDecimal pvcVal;
	private String lngVal;
	private BigDecimal sttVal;
	private BigDecimal cmpVal;

	public static final OgrsZotPK get(OGrsZotS s) {
	    	return OgrsZotPK.builder().cnyVal(s.getCnyVal()).pvcVal(s.getPvcVal()).build();
	    }
	

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("cnyVal", cnyVal);
		map.put("pvcVal", pvcVal);
		
		if(lngVal!=null) {
		    map.put("lngVal", lngVal);
		}
		
		if(sttVal!=null) {
		    map.put("sttVal", sttVal);
		}
		if(cmpVal!=null) {
		    map.put("cmpVal", cmpVal);
		}
		
		return map;
	}

}
