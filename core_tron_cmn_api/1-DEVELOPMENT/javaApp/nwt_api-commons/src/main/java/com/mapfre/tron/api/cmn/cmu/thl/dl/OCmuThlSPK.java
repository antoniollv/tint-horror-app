package com.mapfre.tron.api.cmn.cmu.thl.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

/**
 * The OCmuThlSPK.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Oct 2021 - 15:10:26
 *
 */
@Data
@Builder
public class OCmuThlSPK implements NewtronPK {

    /** Code company.*/
    private Integer cmpVal;

    /** Language code.*/
    private String lngVal;

    /** User code.*/
    private String usrVal;

    /** First Level.*/
    private BigDecimal frsLvlVal;

    /** Second Level.*/
    private BigDecimal scnLvlVal;

    /** Third level value. */
    private BigDecimal thrLvlVal;

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

        if (thrLvlVal != null) {
            map.put("thrLvlVal", thrLvlVal);
        }

        return map;
    }

}
