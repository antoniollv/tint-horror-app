package com.mapfre.tron.api.fdf.rfd.dl;

import java.math.BigDecimal;
import java.util.Date;
import com.mapfre.nwt.trn.fdf.rfd.bo.OFdfRfdS;


public interface IDlFdfRfdDAO {

    OFdfRfdS get(BigDecimal cmpVal, String frdRsnVal,  String usrLngVal, Date vldDat);
   
}
