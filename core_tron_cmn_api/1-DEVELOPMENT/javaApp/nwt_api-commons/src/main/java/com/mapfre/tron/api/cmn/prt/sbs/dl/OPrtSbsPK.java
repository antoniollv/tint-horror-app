package com.mapfre.tron.api.cmn.prt.sbs.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class OPrtSbsPK implements NewtronPK {

	private BigDecimal  cmpVal;
        private BigDecimal  secVal;
        
        public static final OPrtSbsPK get(OPrtSbsS s) {
        	return OPrtSbsPK.builder().cmpVal(s.getCmpVal()).secVal(s.getSecVal()).build();
        }
    
    	@Override
    	public Map<String, Object> asMap() {
    		Map<String, Object> map = new HashMap<>();
    		map.put("cmpVal", cmpVal);
    		map.put("secVal", secVal);
    		return map;
    	}
}
