package com.mapfre.tron.api.cmn.scn.lvl.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

/**
 * The OCmuSnlSPK.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 21 Oct 2021 - 12:13:09
 *
 */
@Data
@Builder
public class OCmuSnlSPK implements NewtronPK {

    /** Code company.*/
    private Integer cmpVal;

    /** Language code.*/
    private String lngVal;

    /** User code.*/
    private String usrVal;

    /** First Level.*/
    private BigDecimal frsLvlVal;

    /** First Level.*/
    private BigDecimal scnLvlVal;

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();

        if (cmpVal != null) {
            map.put("cmpVal", cmpVal);
        }

        if (lngVal != null) {
            map.put("lngVal", lngVal);
        }

        if (usrVal != null) {
            map.put("usrVal", usrVal);
        }

        if (frsLvlVal != null) {
            map.put("frsLvlVal", frsLvlVal);
        }

        if (scnLvlVal != null) {
            map.put("scnLvlVal", scnLvlVal);
        }

        return map;
    }

}
