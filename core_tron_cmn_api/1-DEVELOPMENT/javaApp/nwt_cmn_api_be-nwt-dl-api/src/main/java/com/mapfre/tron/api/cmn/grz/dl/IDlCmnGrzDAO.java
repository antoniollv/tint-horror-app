package com.mapfre.tron.api.cmn.grz.dl;

import java.math.BigDecimal;
import java.util.List;
import com.mapfre.nwt.trn.cmn.grz.bo.OCmnGrzS;

public interface IDlCmnGrzDAO {

    List<OCmnGrzS> get_grzPT(BigDecimal cmpVal, String grzVal, String usrLngVal);

    OCmnGrzS get(BigDecimal cmpVal, String grzVal, String grzTypVal, String usrLngVal);
    
    OCmnGrzS get_grzVal(BigDecimal cmpVal, String grzVal, String usrLngVal);

}
