package com.mapfre.tron.api.cmn.mkt.dl;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.cmn.mkt.bo.OCmnMktS;

public interface IDlCmnMktDao {
    
    OCmnMktS get(BigDecimal cmpVal, BigDecimal mktVal, String lngVal, Date vldDat,  String usrLngVal);

    OCmnMktS get_002(BigDecimal cmpVal, BigDecimal mktVal, String lngVal, Date vldDat, String usrLngVal);
}
