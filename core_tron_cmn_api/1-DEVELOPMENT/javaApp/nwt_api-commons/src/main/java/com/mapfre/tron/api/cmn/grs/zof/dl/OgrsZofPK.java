package com.mapfre.tron.api.cmn.grs.zof.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;

import lombok.Builder;
import lombok.Data;


@Data 
@Builder
public class OgrsZofPK implements NewtronPK {
    
    	private BigDecimal cmpVal;
	private String usrVal;
	private String lngVal;
	private String pslCodVal;
	private String reaPsc;
	private String cnyVal;
	private BigDecimal sttVal;
	private BigDecimal pvcVal;
	private BigDecimal twnVal;
	public static final OgrsZofPK get(OGrsZofS s) {
	    	return OgrsZofPK.builder().pslCodVal(s.getPslCodVal()).reaPsc(s.getReaPsc()).cnyVal(s.getCnyVal()).sttVal(s.getSttVal()).pvcVal(s.getPvcVal()).twnVal(s.getTwnVal()).build();
	    }
	

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("pslCodVal", pslCodVal);
		map.put("reaPsc", reaPsc);
		map.put("cnyVal", cnyVal);
		map.put("sttVal", sttVal);
		map.put("pvcVal", pvcVal);
		map.put("twnVal", twnVal);
		if(cmpVal!=null) {
		    map.put("cmpVal", cmpVal);
		}
		
		if(usrVal!=null) {
		    map.put("usrVal", usrVal);
		}
		
		if(lngVal!=null) {
		    map.put("lngVal", lngVal);
		}
		
		return map;
	}

}
