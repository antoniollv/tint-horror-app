package com.mapfre.tron.api.cmn.rge.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.cmn.rge.bo.OCmnRgeS;

public interface IDlCmnRgeDAO {
      
    OCmnRgeS getRgeVldVal(BigDecimal cmpVal, String rgeVldVal, String usrLngVal);

}
