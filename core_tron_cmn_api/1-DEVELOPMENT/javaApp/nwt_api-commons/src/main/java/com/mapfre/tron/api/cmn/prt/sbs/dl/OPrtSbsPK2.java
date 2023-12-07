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
public class OPrtSbsPK2 implements NewtronPK {
        
        
        private BigDecimal cmpVal; 
        private BigDecimal secVal; 
        private BigDecimal sbsVal; 
        private String lngVal;
        private String usrVal;
        
        
        
        public static final OPrtSbsPK2 get(OPrtSbsS s) {
        	return OPrtSbsPK2.builder().cmpVal(s.getCmpVal()).secVal(s.getSecVal()).sbsVal(s.getSbsVal()).build();
        }
    
    	@Override
    	public Map<String, Object> asMap() {
    		Map<String, Object> map = new HashMap<>();
    		map.put("cmpVal", cmpVal);
    		map.put("secVal", secVal);
    		map.put("sbsVal", sbsVal);
    		return map;
    	}
}
