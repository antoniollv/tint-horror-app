package com.mapfre.tron.api.acf.dag.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.acf.dag.bo.OAcfDagS;

public interface IDlAcfDagDAO {

    // Obtener moneda
    OAcfDagS get_moneda(BigDecimal cmpVal, BigDecimal crnVal, String usrLngVal);
    
    //Obtener concepto cobro
    
    OAcfDagS get_cobro(BigDecimal cmpVal, String cloPymCncVal, String usrLngVal);

}
