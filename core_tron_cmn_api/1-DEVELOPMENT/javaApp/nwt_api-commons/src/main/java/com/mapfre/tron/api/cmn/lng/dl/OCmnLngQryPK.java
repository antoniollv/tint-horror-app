package com.mapfre.tron.api.cmn.lng.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngP;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniS;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@SuppressWarnings("serial")
public class OCmnLngQryPK implements NewtronPK {
    
    private BigDecimal  cmpVal;
    private String  lngVal;

    /**
     * Getter for <code>com.mapfre.tron.crn.crn.OCrnCrnPK</code>.
     *
     * @param pmOCrnCrnS the data property
     * @return the primary key class
     */
    public static final OCmnLngQryPK get(final OCmnLngP pmOCmnLngP) {
        return OCmnLngQryPK.builder().build();
    }
    
    public static final OCmnLngQryPK get(final OCmnLngS s) {
        return OCmnLngQryPK.builder().build();
    }

    /**
     * Getter for <code>com.mapfre.tron.crn.crn.OCrnCrnPK</code>.
     *
     * @param pmOPlyGniS the data property
     * @return the primary key class
     */
    public static final OCmnLngQryPK get(final OPlyGniS pmOPlyGniS) {
        return OCmnLngQryPK.builder().build();
    }

    /**
     * Get the primary key map.
     *
     * @return the primary key map
     */
    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("cmpVal", cmpVal);
	map.put("lngVal", lngVal);
        return map;
    }
}
