package com.mapfre.tron.api.cmn.abd.sbm.dl.impl;

import java.math.BigDecimal;
import java.util.Date;
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
public class VhtNamPK implements NewtronPK {

	
	/** Code company.*/
	    private BigDecimal cmpVal;

	    /** Language code.*/
	    private Integer vhtVal;

	    /** User code.*/
	    private Date vldDat;

	    public static final VhtNamPK get(BigDecimal cmpVal, Integer vhtVal, Date vldDat) {
	    	return VhtNamPK.builder().cmpVal(cmpVal).vhtVal(vhtVal).vldDat(vldDat).build();
	    }

	    @Override
	    public Map<String, Object> asMap() {
	        Map<String, Object> map = new HashMap<>();

	        if (cmpVal != null) {
	            map.put("cmpVal", cmpVal);
	        }

	        if (vhtVal != null) {
	            map.put("vhtVal", vhtVal);
	        }

	        if (vldDat != null) {
	            map.put("vldDat", vldDat);
	        }

	        return map;
	    }
}
