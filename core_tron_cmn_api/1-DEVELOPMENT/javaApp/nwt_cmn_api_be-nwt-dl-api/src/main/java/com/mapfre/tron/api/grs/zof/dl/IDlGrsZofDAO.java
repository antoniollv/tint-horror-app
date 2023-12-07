package com.mapfre.tron.api.grs.zof.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;

public interface IDlGrsZofDAO {
	
	public OGrsZofS get(BigDecimal cmpVal, String cnyVal, BigDecimal sttVal, BigDecimal pvcVal, BigDecimal twnVal, String pslCodVal, String usrLngVal);

}
