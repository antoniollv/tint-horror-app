package com.mapfre.tron.api.cmn.prt.lob.dl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class OPrtLobVdaPK implements NewtronPK {

	private BigDecimal cmpVal; 
	private BigDecimal lobVal;
	private Date vldDat;
	private String lngVal;
	
	public static final OPrtLobVdaPK get(OPrtLobS s) {
    		return OPrtLobVdaPK.builder().lobVal(s.getLobVal()).cmpVal(s.getCmpVal())
    			.vldDat(s.getVldDat()).build();
	}

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("lobVal", lobVal);
		map.put("cmpVal", cmpVal);
		map.put("vldDat", vldDat);
		if(lngVal!=null) {
		    map.put("lngVal", lngVal);
		}
		return map;
	}
}
