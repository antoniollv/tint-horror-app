package com.mapfre.tron.api.ard.aby.dl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mapfre.nwt.commons.dl.NewtronPK;
import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvS;

import lombok.Builder;
import lombok.Data;

@Data 
@Builder
public class OArdAbvQryPK implements NewtronPK {

	private BigDecimal cmpVal; 
	private BigDecimal lobVal;
	private BigDecimal mdtVal; 
	private String fldNam;
	private String lngVal;
	
	public static final OArdAbvQryPK get(OArdAbvS s) {
    	return OArdAbvQryPK.builder().fldNam(s.getFldNam()).mdtVal(s.getMdtVal()).lobVal(s.getLobVal())
    		.cmpVal(s.getCmpVal()).build();
    }

	@Override
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("fldNam", fldNam);
		map.put("mdtVal", mdtVal);
		map.put("lobVal", lobVal);
		map.put("cmpVal", cmpVal);
		if(lngVal!=null) {
		    map.put("lngVal", lngVal);
		}
		return map;
	}
}
