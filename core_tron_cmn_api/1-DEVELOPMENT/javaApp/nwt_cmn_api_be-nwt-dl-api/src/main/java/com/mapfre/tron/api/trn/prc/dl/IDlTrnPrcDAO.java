package com.mapfre.tron.api.trn.prc.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;

public interface IDlTrnPrcDAO {
    
    //Consultar procedimiento
    OTrnPrcS getNomPrg(String nomPrg, String usrLngVal, BigDecimal cmpVal);

}
