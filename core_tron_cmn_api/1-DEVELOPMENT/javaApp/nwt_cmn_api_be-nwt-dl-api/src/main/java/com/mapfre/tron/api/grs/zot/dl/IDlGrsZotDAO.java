package com.mapfre.tron.api.grs.zot.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;

public interface IDlGrsZotDAO {
	
	public OGrsZotS get(BigDecimal cmpVal, String cnyVal, BigDecimal sttVal, BigDecimal pvcVal, String usrLngVal);

}
