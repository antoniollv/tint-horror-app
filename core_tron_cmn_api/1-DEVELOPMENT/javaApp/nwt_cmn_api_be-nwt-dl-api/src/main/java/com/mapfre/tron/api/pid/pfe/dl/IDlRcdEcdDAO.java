package com.mapfre.tron.api.pid.pfe.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.rcd.ecd.bo.ORcdEcdS;

public interface IDlRcdEcdDAO {

    // Obtener concepto economico
    ORcdEcdS get_eccVal(BigDecimal cmpVal, BigDecimal eccVal, String usrLngVal);
    
    //Obtener desglose 4
    
    ORcdEcdS get_ecmBrwCncVal4(BigDecimal cmpVal, BigDecimal ecmBrwCncVal, BigDecimal eccVal, String usrLngVal);
    
    List<OCmnTypS> get(BigDecimal cmpVal, String fldVal, String itcVal, String usrLngVal);

}
