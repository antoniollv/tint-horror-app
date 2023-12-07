package com.mapfre.tron.api.cmn.psf.psf.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniS;
import com.mapfre.nwt.trn.psf.psf.bo.OPsfPsfS;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class OPsfPsfPK implements NewtronPK {

	private BigDecimal  cmpVal;
	private BigDecimal  pmsVal;
   
    public static final OPsfPsfPK get(OPsfPsfS s) {
    	return OPsfPsfPK.builder().cmpVal(s.getCmpVal()).pmsVal(s.getPmsVal()).build();
    }
    
    public static final OPsfPsfPK get(OPlyGniS s) {
    	return OPsfPsfPK.builder().cmpVal(s.getCmpVal()).pmsVal(s.getPmsVal()).build();
    }

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("cmpVal", cmpVal);
		map.put("pmsVal", pmsVal);
		return map;
	}
}

