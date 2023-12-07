package com.mapfre.tron.api.cmn.prt.del.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

/**
 * The OPrtDel PK.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 12:53:41
 *
 */
@Data 
@Builder
public class OPrtDelPK implements NewtronPK {

    /** Code company.*/
    private BigDecimal cmpVal;

    /** Numero de contrato.*/
    private BigDecimal delVal;

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();

        if (cmpVal != null) {
            map.put("cmpVal", cmpVal);
        }

        if (delVal != null) {
            map.put("delVal", delVal);
        }

        return map;
    }

}
