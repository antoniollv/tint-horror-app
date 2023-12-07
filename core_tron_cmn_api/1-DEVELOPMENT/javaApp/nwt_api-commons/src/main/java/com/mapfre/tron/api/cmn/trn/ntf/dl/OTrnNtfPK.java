package com.mapfre.tron.api.cmn.trn.ntf.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

/**
 * The TrnNtf PK.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 12:04:32
 *
 */
@Data 
@Builder
public class OTrnNtfPK implements NewtronPK {

    /** Code company.*/
    private BigDecimal cmpVal;

    /** Dcn code.*/
    private String dcnVal;

    /** Language code.*/
    private String lngVal;

    /** Operation Identification value.*/
    private String oprIdnVal;
    
    /** Operation Identification value.*/
    private String encVal;

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();

        if (cmpVal != null) {
            map.put("cmpVal", cmpVal);
        }

        if (dcnVal != null) {
            map.put("dcnVal", dcnVal);
        }

        if (lngVal != null) {
            map.put("lngVal", lngVal);
        }

        if (oprIdnVal != null) {
            map.put("oprIdnVal", oprIdnVal);
        }
        
        if (encVal != null) {
            map.put("encVal", encVal);
        }

        return map;
    }

}
