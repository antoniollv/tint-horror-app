package com.mapfre.tron.api.cmn.men.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenS;
import com.mapfre.nwt.trn.trn.pgm.bo.OTrnPgmS;

public interface OCmnMenDao {
    
    public List<OCmnMenS> getAll(BigDecimal cmpVal, String usrVal, String lngVal);
    
    public String getUrlBase(String urlBase);
    
    public List<String> getOprIdnValLst(String usrVal, BigDecimal cmpVal);
    
    // validar agrValPrn
    OCmnMenS get_agrValPrn(BigDecimal cmpVal, String agrValPrn, String usrLngVal) ;

    //Validar oprIdnVal
    OCmnMenS get_oprIdnVal(BigDecimal cmpVal, String oprIdnVal, String usrLngVal);

    //Validar secuencia menSqnVal
    OCmnMenS get_menSqnVal(BigDecimal cmpVal, String agrValPrn, BigDecimal menSqnVal, String usrLngVal);
}
