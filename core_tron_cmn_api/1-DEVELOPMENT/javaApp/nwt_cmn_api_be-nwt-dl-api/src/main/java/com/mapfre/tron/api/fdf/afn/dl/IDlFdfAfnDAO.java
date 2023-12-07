package com.mapfre.tron.api.fdf.afn.dl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.mapfre.nwt.trn.fdf.afn.bo.OFdfAfnS;


public interface IDlFdfAfnDAO {

    OFdfAfnS get(BigDecimal cmpVal, String afrNdcVal, Date vldDat,  String usrLngVal);
    
    OFdfAfnS get_afp(BigDecimal cmpVal, String afrNdcVal, String lngVal,  String usrLngVal);
    
    List<OFdfAfnS> get_afnPT(BigDecimal cmpVal, String afrNdcVal, String usrLngVal);
    
}
