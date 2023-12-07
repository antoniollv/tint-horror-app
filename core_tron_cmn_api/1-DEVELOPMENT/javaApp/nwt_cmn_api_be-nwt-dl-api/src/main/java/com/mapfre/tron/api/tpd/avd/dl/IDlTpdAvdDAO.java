package com.mapfre.tron.api.tpd.avd.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.tpd.avd.bo.OTpdAvdS;

public interface IDlTpdAvdDAO {

    OTpdAvdS get(BigDecimal cmpVal, BigDecimal thpAcvVal, String usrLngVal);
    
    OTpdAvdS get_stuGrpVal(BigDecimal cmpVal, String stuGrpVal, String usrLngVal);

    OTpdAvdS get_TpdTpa(BigDecimal cmpVal, BigDecimal thpAcvVal, String usrLngVal);
}
