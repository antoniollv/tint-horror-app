package com.mapfre.tron.api.cmn.cmu.thl.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniS;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class OCmuThlPK implements NewtronPK {

	private BigDecimal  cmpVal;
	private BigDecimal  thrLvlVal;
   
    public static final OCmuThlPK get(OCmuThlS s) {
    	return OCmuThlPK.builder().cmpVal(s.getCmpVal()).thrLvlVal(s.getThrLvlVal()).build();
    } 
    
    public static final OCmuThlPK get(OPlyGniS s) {
    	return OCmuThlPK.builder().cmpVal(s.getCmpVal()).thrLvlVal(s.getCptThrLvlVal()).build();
    } 

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("cmpVal", cmpVal);
		map.put("thrLvlVal", thrLvlVal);
		return map;
	}
}
