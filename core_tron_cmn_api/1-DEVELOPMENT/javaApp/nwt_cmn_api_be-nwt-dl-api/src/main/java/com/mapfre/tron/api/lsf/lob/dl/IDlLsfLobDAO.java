package com.mapfre.tron.api.lsf.lob.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;

public interface IDlLsfLobDAO {
	
	//Consultar procedimiento
    List<OTrnPrcS> getNomPrg(String nomPrg, String usrLngVal, BigDecimal cmpVal, String name);
}
