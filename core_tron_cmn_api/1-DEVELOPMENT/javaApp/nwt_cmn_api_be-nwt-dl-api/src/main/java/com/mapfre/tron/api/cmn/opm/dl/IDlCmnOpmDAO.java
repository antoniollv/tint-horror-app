package com.mapfre.tron.api.cmn.opm.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.cmn.opm.bo.OCmnOpmS;

public interface IDlCmnOpmDAO {

    //Consultar Instalación
    OCmnOpmS get_insVal(BigDecimal cmpVal, String insVal, String usrLngVal);

}
