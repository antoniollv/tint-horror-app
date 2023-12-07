package com.mapfre.tron.api.tpd.icf.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.tpd.icf.bo.OTpdIcfS;

public interface IDlTpdIcfDAO {

    OTpdIcfS get(BigDecimal cmpVal, BigDecimal lobVal, String usrLngVal);
    
    OTpdIcfS get_002(BigDecimal cmpVal, String clfVal, String iccTypVal, String usrLngVal);
}
