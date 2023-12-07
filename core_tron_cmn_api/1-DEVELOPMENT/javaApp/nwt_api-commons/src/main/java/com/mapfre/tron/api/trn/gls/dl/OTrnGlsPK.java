package com.mapfre.tron.api.trn.gls.dl;

import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.trn.trn.gls.bo.OTrnGlsS;
import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;
import com.mapfre.tron.api.cmn.dl.NewtronPK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder
@AllArgsConstructor
public class OTrnGlsPK implements NewtronPK {

    /**
     * 
     */
    private static final long serialVersionUID = 4527167678109949026L;
    
    private String temVal;
    private String lngVal;
    
    @Override
    public Map<String, Object> asMap() {
	Map<String, Object> map = new HashMap<>();
	map.put("lngVal", lngVal);
	map.put("temVal", temVal);
	return map;
    }

    public static final OTrnGlsPK get(OTrnGlsS glsS, OTrnCnxS cnxS) {
    	return OTrnGlsPK.builder().temVal(glsS.getTemVal()).lngVal(cnxS.getLngVal()).build();
    }
}
