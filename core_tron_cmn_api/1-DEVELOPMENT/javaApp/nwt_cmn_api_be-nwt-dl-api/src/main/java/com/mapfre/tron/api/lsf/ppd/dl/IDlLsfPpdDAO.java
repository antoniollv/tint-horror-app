package com.mapfre.tron.api.lsf.ppd.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.lsf.ppd.bo.OLsfPpdS;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnTypPK;

public interface IDlLsfPpdDAO {

	OLsfPpdS getFilTypVal(BigDecimal cmpVal, BigDecimal lobVal, String filTypVal, String usrLngVal);
	
	OCmnTypS getCmnTypS(String fldNam, String itcVal, String lngVal, BigDecimal cmpVal, String prpIdn);
	
}
