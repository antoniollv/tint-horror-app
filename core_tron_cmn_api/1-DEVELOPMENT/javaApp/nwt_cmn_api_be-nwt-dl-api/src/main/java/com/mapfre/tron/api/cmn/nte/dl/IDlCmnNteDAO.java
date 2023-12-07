package com.mapfre.tron.api.cmn.nte.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;

public interface IDlCmnNteDAO {
    
    OCmnNteS get_003(BigDecimal cmpVal, String nteVal, String usrLngVal);

}
