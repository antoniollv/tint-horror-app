package com.mapfre.tron.api.cmn.crn.crn.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;
import com.mapfre.nwt.trn.ply.gni.bo.OPlyGniS;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@SuppressWarnings("serial")
public class OCrnCrnPK implements NewtronPK {

    /** The primary key property.*/
    private BigDecimal crnVal;
    private String lngVal;
    private BigDecimal cmpVal;
    private String sdrCrnVal;
    /**
     * Getter for <code>com.mapfre.tron.crn.crn.OCrnCrnPK</code>.
     *
     * @param pmOCrnCrnS the data property
     * @return the primary key class
     */
    public static final OCrnCrnPK get(final OCrnCrnS pmOCrnCrnS) {
        return OCrnCrnPK.builder().crnVal(pmOCrnCrnS.getCrnVal()).build();
    }

    /**
     * Getter for <code>com.mapfre.tron.crn.crn.OCrnCrnPK</code>.
     *
     * @param pmOPlyGniS the data property
     * @return the primary key class
     */
    public static final OCrnCrnPK get(final OPlyGniS pmOPlyGniS) {
        return OCrnCrnPK.builder().crnVal(pmOPlyGniS.getCrnVal()).build();
    }
    


    /**
     * Get the primary key map.
     *
     * @return the primary key map
     */
    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("crnVal", crnVal);
        map.put("cmpVal", cmpVal);
        map.put("sdrCrnVal", sdrCrnVal);
        if(lngVal != null) {
            map.put("lngVal", lngVal);
        }

        return map;
    }
}
