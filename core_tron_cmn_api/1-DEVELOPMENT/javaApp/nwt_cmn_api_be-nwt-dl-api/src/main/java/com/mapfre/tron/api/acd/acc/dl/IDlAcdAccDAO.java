package com.mapfre.tron.api.acd.acc.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.acd.acc.bo.OAcdAccS;

public interface IDlAcdAccDAO {

    OAcdAccS get(BigDecimal cmpVal, String acgGrgVal, String cloPymCncVal, String usrLngVal);
    
    OAcdAccS get_TpdTap(BigDecimal cmpVal, String cloPymCncVal, String acgGrgVal, String usrLngVal);

}
