package com.mapfre.tron.api.cmn.cmu.fsl.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

/**
 * The CmuFslSPK.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 Oct 2021 - 14:12:11
 *
 */
@Data
@Builder
public class OCmuFslSPK implements NewtronPK {

    /** Code company.*/
    private Integer cmpVal;

    /** Language code.*/
    private String lngVal;

    /** User code.*/
    private String usrVal;

    /** First level value.*/
    private BigDecimal frsLvlVal;

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

        return map;
    }

}
