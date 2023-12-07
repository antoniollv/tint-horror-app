package com.mapfre.tron.api.fdf.ccl.dl;

import java.math.BigDecimal;
import java.util.Date;
import com.mapfre.nwt.trn.fdf.fdf.bo.OFdfFdfS;


public interface IDlFdfCclDAO {

    OFdfFdfS get(BigDecimal cmpVal, String frdCclTypVal,  String usrLngVal, Date vldDat);
   
}
