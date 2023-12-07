package com.mapfre.tron.api.cmn.cmu.cmp.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniS;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class OCmuCmpPK implements NewtronPK {

	private BigDecimal  cmpVal;
   
    public static final OCmuCmpPK get(OCmuCmpS s) {
    	return OCmuCmpPK.builder().cmpVal(s.getCmpVal()).build();
    }
    
    public static final OCmuCmpPK get(OPlyGniS s) {
    	return OCmuCmpPK.builder().cmpVal(s.getCmpVal()).build();
    }
  
	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("cmpVal", cmpVal);
		return map;
	}
}
