package com.mapfre.tron.api.trn.syp.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.syp.bo.OTrnSypS;

public interface IDlTrnSypDAO {

    OTrnSypS get(BigDecimal cmpVal, String stuVal, String usrLngVal);

}
