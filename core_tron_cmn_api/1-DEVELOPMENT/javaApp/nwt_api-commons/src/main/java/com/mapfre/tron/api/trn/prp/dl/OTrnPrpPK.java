package com.mapfre.tron.api.trn.prp.dl;

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
public class OTrnPrpPK implements NewtronPK {

    /**
     * 
     */
    private static final long serialVersionUID = 4527167678109949026L;
    
    private String prpIdn;
    private String lngVal;
    
    @Override
    public Map<String, Object> asMap() {
	Map<String, Object> map = new HashMap<>();
	map.put("lngVal", lngVal);
	map.put("prpIdn", prpIdn);
	return map;
    }

    public static final OTrnPrpPK get(OTrnGlsS prpS, OTrnCnxS cnxS) {
    	return OTrnPrpPK.builder().prpIdn(prpS.getPrpIdn()).lngVal(cnxS.getLngVal()).build();
    }
}
