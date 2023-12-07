package com.mapfre.tron.api.fdf.fdf.dl;

import java.math.BigDecimal;
import java.util.Date;
import com.mapfre.nwt.trn.fdf.fdf.bo.OFdfFdfS;


public interface IDlFdfFdfDAO {

    OFdfFdfS get(BigDecimal cmpVal, String frdTypVal,  String usrLngVal, Date vldDat);
   
}
