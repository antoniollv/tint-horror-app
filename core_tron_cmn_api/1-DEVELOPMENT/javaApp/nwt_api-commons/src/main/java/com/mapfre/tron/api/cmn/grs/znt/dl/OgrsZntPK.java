package com.mapfre.tron.api.cmn.grs.znt.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;

import lombok.Builder;
import lombok.Data;


@Data 
@Builder
public class OgrsZntPK implements NewtronPK {
    
    	private BigDecimal cmpVal;
	private String usrVal;
	private String lngVal;
	private String cnyVal;
	private BigDecimal sttVal;
	
	public static final OgrsZntPK get(OGrsZntS s) {
	    	return OgrsZntPK.builder().cnyVal(s.getCnyVal()).sttVal(s.getSttVal()).build();
	    }
	

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("cnyVal", cnyVal);
		map.put("sttVal", sttVal);
		
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
