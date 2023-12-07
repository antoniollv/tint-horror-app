package com.mapfre.tron.api.rcd.exd.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.rcd.exd.bo.ORcdExdS;

public interface IDlRcdExdDAO {

    //Consultar Agente
    ORcdExdS get(BigDecimal cmpVal, BigDecimal agnVal, String usrLngVal);

    //Consultar Sector
    ORcdExdS getSector(BigDecimal cmpVal, BigDecimal secVal, String usrLngVal);

}
