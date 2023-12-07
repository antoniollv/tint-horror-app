package com.mapfre.tron.api.cmn.gtt.dl;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.cmn.gtt.bo.OCmnGttS;


public interface IDlCmnGttDao {
    
    OCmnGttS get(BigDecimal cmpVal, BigDecimal gttVal, String lngVal, Date vldDat,  String usrLngVal);

}
