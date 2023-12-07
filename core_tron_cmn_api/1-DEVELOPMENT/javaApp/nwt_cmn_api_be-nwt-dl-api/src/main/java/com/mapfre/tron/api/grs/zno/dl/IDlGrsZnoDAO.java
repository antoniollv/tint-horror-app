package com.mapfre.tron.api.grs.zno.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;

public interface IDlGrsZnoDAO {
	
	public OGrsZnoS get(BigDecimal cmpVal, String cnyVal, String usrLngVal);

}
