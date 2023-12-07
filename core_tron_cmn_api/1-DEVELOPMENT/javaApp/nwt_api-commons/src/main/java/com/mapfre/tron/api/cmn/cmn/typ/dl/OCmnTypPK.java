package com.mapfre.tron.api.cmn.cmn.typ.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class OCmnTypPK implements NewtronPK {

	private String fldNam;
	private String itcVal;
	private String lngVal;
	private BigDecimal lobVal;
	private BigDecimal cmpVal;
	
	public static final OCmnTypPK get(OCmnTypS s) {
    	return OCmnTypPK.builder().fldNam(s.getFldNam()).itcVal(s.getItcVal()).lngVal(s.getLngVal()).build();
    }

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("fldNam", fldNam);
		map.put("itcVal", itcVal);
		map.put("lngVal", lngVal);
		map.put("cmpVal", cmpVal);
		if(lobVal != null) map.put("lobVal", lobVal.toString());
		return map;
	}
}
