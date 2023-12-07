package com.mapfre.tron.api.grs.znf.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;

public interface IDlGrsZnfDAO {
	
	public OGrsZnfS get(BigDecimal cmpVal, String cnyVal, BigDecimal pvcVal, BigDecimal twnVal, String usrLngVal);

}
