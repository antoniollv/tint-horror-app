package com.mapfre.tron.api.trn.err.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.NewtronPK;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data @Builder
@AllArgsConstructor
public class OTrnErrPK implements NewtronPK {

    /**
     * 
     */
    private static final long serialVersionUID = 4527167678109949026L;
    
    private BigDecimal errVal;
    private String lngVal;
    private BigDecimal cmpVal;
    
    @Override
    public Map<String, Object> asMap() {
	Map<String, Object> map = new HashMap<>();
	map.put("lngVal", lngVal);
	map.put("errVal", errVal);
	map.put("cmpVal", cmpVal);
	return map;
    }

    public static final OTrnErrPK get(OTrnErrS errS, OTrnCnxS cnxS) {
    	return OTrnErrPK.builder().errVal(errS.getErrVal()).lngVal(cnxS.getLngVal()).build();
    }
}
