package com.mapfre.tron.api.cmn.grs.znf.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;

import lombok.Builder;
import lombok.Data;


@Data 
@Builder
public class OgrsZnfPK implements NewtronPK {
    
    	private BigDecimal cmpVal;
	private String usrVal;
	private String lngVal;
	private String cnyVal;
	private BigDecimal pvcVal;
	private BigDecimal twnVal;
	
	public static final OgrsZnfPK get(OGrsZnfS s) {
	    	return OgrsZnfPK.builder().cnyVal(s.getCnyVal()).pvcVal(s.getPvcVal()).twnVal(s.getTwnVal()).build();
	    }
	

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("cnyVal", cnyVal);
		map.put("pvcVal", pvcVal);
		map.put("twnVal", twnVal);
		
		if(lngVal!=null) {
		    map.put("cmpVal", cmpVal);
		}
		
		if(lngVal!=null) {
		    map.put("usrVal", usrVal);
		}
		
		if(lngVal!=null) {
		    map.put("lngVal", lngVal);
		}
		
		return map;
	}

}
