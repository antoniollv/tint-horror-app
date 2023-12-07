package com.mapfre.tron.api.cmn.prt.sbl.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

/**
 * The PrtSbl PK.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 07:38:40
 *
 */
@Data 
@Builder
public class OPrtSblPK implements NewtronPK {

    /** Code company.*/
    private BigDecimal cmpVal;

    /** Numero de contrato.*/
    private BigDecimal sblVal;

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();

        if (cmpVal != null) {
            map.put("cmpVal", cmpVal);
        }

        if (sblVal != null) {
            map.put("sblVal", sblVal);
        }

        return map;
    }

}
