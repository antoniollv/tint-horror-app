package com.mapfre.tron.api.cmn.prt.sec.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniS;
import com.mapfre.nwt.trn.prt.sec.bo.OPrtSecS;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class OPrtSecPK implements NewtronPK {

	private BigDecimal  cmpVal;
        private BigDecimal  secVal;
        
        public static final OPrtSecPK get(OPlyGniS s) {
        	return OPrtSecPK.builder().cmpVal(s.getCmpVal()).secVal(s.getSecVal()).build();
        }
        
        public static final OPrtSecPK get(OPrtSecS s) {
        	return OPrtSecPK.builder().cmpVal(s.getCmpVal()).secVal(s.getSecVal()).build();
        }
    
    	@Override
    	public Map<String, Object> asMap() {
    		Map<String, Object> map = new HashMap<>();
    		map.put("cmpVal", cmpVal);
    		map.put("secVal", secVal);
    		return map;
    	}
}
