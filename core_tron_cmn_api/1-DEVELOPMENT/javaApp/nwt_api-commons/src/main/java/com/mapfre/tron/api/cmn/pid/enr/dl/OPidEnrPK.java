package com.mapfre.tron.api.cmn.pid.enr.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

/**
 * The PidEnr PK.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 10:27:12
 *
 */
@Data 
@Builder
public class OPidEnrPK implements NewtronPK {

    /** Code company.*/
    private BigDecimal cmpVal;

    /** Endorserment value.*/
    private BigDecimal enrVal;

    /** Subendrosemnt value.*/
    private BigDecimal enrSbdVal;

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();

        if (cmpVal != null) {
            map.put("cmpVal", cmpVal);
        }

        if (enrVal != null) {
            map.put("enrVal", enrVal);
        }

        if (enrSbdVal != null) {
            map.put("enrSbdVal", enrSbdVal);
        }

        return map;
    }

}
