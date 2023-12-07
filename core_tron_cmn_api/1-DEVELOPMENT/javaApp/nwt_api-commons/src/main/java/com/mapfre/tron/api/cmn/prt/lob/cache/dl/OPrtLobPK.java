package com.mapfre.tron.api.cmn.prt.lob.cache.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniS;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class OPrtLobPK implements NewtronPK {
    
    private BigDecimal cmpVal;
    private BigDecimal lobVal;
    private String lngVal;
    
    public static final OPrtLobPK get(OPlyGniS s) {
        return OPrtLobPK.builder().cmpVal(s.getCmpVal()).lobVal(s.getLobVal()).build();
    }
    
    public static final OPrtLobPK get(OPrtLobS s) {
        return OPrtLobPK.builder().cmpVal(s.getCmpVal()).lobVal(s.getLobVal()).build();
    }
    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("cmpVal", cmpVal);
        map.put("lobVal", lobVal);
        if(lngVal !=null) {
            map.put("lngVal", lngVal);
        }
        return map;
    }
}
