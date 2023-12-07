package com.mapfre.tron.api.cmn.ard.fxa.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;

import lombok.Builder;
import lombok.Data;

/**
 * The fixed attributes controller.
 *
 * @author arquitectura - izhan del rio
 * @since 1.8
 * @version 12 Nov 2021 - 16:17:02
 *
 */

@Data 
@Builder
public class OArdFxaPK implements NewtronPK {

	
	/** Code company.*/
	    private BigDecimal cmpVal;

	    /** Language code.*/
	    private String lngVal;

	    /** User code.*/
	    private String usrVal;

	    public static final OArdFxaPK get(BigDecimal cmpVal, String lngVal, String usrVal) {
	    	return OArdFxaPK.builder().cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal).build();
	    }

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

	        return map;
	    }
}
