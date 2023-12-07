package com.mapfre.tron.api.tpd.sps.dl;

import java.math.BigDecimal;
import java.util.Date;
import com.mapfre.nwt.trn.tpd.sps.bo.OTpdSpsS;

public interface IDlTpdSpsDAO {

    OTpdSpsS get(BigDecimal cmpVal, String tylVal, String lngVal, String usrLngVal);
    
    OTpdSpsS get_splCtg(BigDecimal cmpVal, String splCtgVal, String lngVal, String usrLngVal);
    
    OTpdSpsS get_tylTya(BigDecimal cmpVal, BigDecimal thpAcvVal, String tylVal, String usrLngVal);
    
    OTpdSpsS get_spsTca(BigDecimal cmpVal, BigDecimal thpAcvVal, String tylVal, String splCtgVal, Date vldDat, String usrLngVal);

    OTpdSpsS get_007(BigDecimal cmpVal, BigDecimal thpAcvVal, String tylVal, String splCtgVal, Date vldDat, String usrLngVal);
}
