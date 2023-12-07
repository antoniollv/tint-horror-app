package com.mapfre.tron.api.grs.znt.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;

public interface IDlGrsZntDAO {
	
	public OGrsZntS get(BigDecimal cmpVal, String cnyVal, BigDecimal sttVal, String usrLngVal);

}
