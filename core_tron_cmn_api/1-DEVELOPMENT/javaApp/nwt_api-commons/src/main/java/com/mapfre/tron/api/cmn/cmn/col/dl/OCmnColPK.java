package com.mapfre.tron.api.cmn.cmn.col.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColP;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class OCmnColPK implements NewtronPK {
    
    /** The primary key property.*/
    protected BigDecimal colVal;
    protected String lngVal;
    protected BigDecimal cmpVal;

    /**
     * Getter for <code>com.mapfre.tron.crn.crn.OCrnCrnPK</code>.
     *
     * @param pmOCrnCrnS the data property
     * @return the primary key class
     */
    public static final OCmnColPK get(final OCmnColP pmOCmnColP) {
        return OCmnColPK.builder().colVal(pmOCmnColP.getOCmnColS().getColVal()).build();
    }

    /**
     * Get the primary key map.
     *
     * @return the primary key map
     */
    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("colVal", colVal);
        if(lngVal !=null) {
            map.put("lngVal", lngVal);
        }
        if(cmpVal !=null) {
            map.put("cmpVal", cmpVal);
        }

        return map;
    }

}
