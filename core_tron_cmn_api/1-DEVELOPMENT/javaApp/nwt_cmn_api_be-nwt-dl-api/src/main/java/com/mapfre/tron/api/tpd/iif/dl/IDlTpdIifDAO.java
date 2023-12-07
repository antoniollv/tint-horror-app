package com.mapfre.tron.api.tpd.iif.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.tpd.iif.bo.OTpdIifS;


public interface IDlTpdIifDAO {

	OTpdIifS getTipoGestion(BigDecimal cmpVal, String mngTypVal, String usrLngVal);

	OTpdIifS getCodigoImpacto(BigDecimal cmpVal, String imcCodTypVal, String usrLngVal);

	
}
