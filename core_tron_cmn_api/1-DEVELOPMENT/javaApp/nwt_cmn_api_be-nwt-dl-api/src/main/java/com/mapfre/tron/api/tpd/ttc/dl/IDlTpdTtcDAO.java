package com.mapfre.tron.api.tpd.ttc.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.tpd.ttc.bo.OTpdTtcS;


public interface IDlTpdTtcDAO {
	
	OTpdTtcS getActividad(BigDecimal cmpVal, BigDecimal thpAcvVal , String usrLngVal);

	OTpdTtcS getRol(BigDecimal cmpVal, String rolVal, String usrLngVal);

	OTpdTtcS getConceptoLogico(BigDecimal cmpVal, String lgpVal, String usrLngVal);

	OTpdTtcS getPropiedad(BigDecimal cmpVal, String prpVal, String usrLngVal);

}
