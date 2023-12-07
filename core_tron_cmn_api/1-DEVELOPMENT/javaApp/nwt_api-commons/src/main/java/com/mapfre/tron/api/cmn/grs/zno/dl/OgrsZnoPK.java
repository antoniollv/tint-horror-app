package com.mapfre.tron.api.cmn.grs.zno.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;

import lombok.Builder;
import lombok.Data;


@Data 
@Builder
public class OgrsZnoPK implements NewtronPK {
    
    	private BigDecimal cmpVal;
	private String usrVal;
	private String lngVal;
	private String cnyVal;
	
	public static final OgrsZnoPK get(OGrsZnoS s) {
	    	return OgrsZnoPK.builder().cnyVal(s.getCnyVal()).build();
	    }
	

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("cnyVal", cnyVal);

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
