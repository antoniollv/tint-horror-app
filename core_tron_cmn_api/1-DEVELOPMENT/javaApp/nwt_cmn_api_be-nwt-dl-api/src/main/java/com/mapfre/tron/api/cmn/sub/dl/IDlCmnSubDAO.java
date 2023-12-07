package com.mapfre.tron.api.cmn.sub.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.cmn.sub.bo.OCmnSubS;


public interface IDlCmnSubDAO {

    OCmnSubS getAgrupacion(BigDecimal cmpVal, String sugVal, String usrLngVal);
    
    OCmnSubS getSublimite(BigDecimal cmpVal, String sugVal, String usrLngVal);
}
