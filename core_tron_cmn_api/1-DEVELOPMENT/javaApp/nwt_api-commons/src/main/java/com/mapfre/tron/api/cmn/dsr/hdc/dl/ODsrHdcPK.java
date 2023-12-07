package com.mapfre.tron.api.cmn.dsr.hdc.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

/**
 * The DsrHdc PK.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 09:43:16
 *
 */
@Data 
@Builder
public class ODsrHdcPK implements NewtronPK {

    /** Code company.*/
    private BigDecimal cmpVal;

    /** Third distribution channel code.*/
    private String thrDstHnlVal;

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();

        if (cmpVal != null) {
            map.put("cmpVal", cmpVal);
        }

        if (thrDstHnlVal != null) {
            map.put("thrDstHnlVal", thrDstHnlVal);
        }

        return map;
    }

}
