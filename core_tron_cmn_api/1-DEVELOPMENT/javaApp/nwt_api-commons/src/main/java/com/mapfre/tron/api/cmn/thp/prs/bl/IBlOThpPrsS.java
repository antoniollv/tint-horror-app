package com.mapfre.tron.api.cmn.thp.prs.bl;

import java.math.BigDecimal;
import java.util.Map;

import com.mapfre.nwt.trn.thp.prs.bo.OThpPrsP;
import com.mapfre.nwt.trn.thp.prs.bo.OThpPrsS;

public interface IBlOThpPrsS {

	OThpPrsP get(String thpDcmTypVal, String thpDcmVal, BigDecimal thpAcvVal, String usrVal, String lngVal,
			BigDecimal cmpVal);
	
	OThpPrsS getCtgNam(Map<String, Object> map);

}
