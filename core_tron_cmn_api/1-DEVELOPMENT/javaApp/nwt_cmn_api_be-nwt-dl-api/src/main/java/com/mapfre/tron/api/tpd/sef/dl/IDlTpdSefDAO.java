package com.mapfre.tron.api.tpd.sef.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.tpd.sef.bo.OTpdSefS;

public interface IDlTpdSefDAO {
    OTpdSefS get(BigDecimal cmpVal, String rteVal, String usrLngVal);
    
    OTpdSefS get_cnc(BigDecimal cmpVal, String rteCncVal, String usrLngVal);
}
