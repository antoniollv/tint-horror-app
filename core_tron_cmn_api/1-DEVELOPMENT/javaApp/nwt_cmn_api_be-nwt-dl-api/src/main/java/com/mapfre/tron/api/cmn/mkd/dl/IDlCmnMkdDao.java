package com.mapfre.tron.api.cmn.mkd.dl;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.cmn.mkd.bo.OCmnMkdS;

public interface IDlCmnMkdDao{

    OCmnMkdS get(BigDecimal cmpVal, BigDecimal mkdVal, String lngVal, Date vldDat,  String usrLngVal);
    
    OCmnMkdS get_002(BigDecimal cmpVal, BigDecimal mkdVal, Date vldDat, String usrLngVal);
}
