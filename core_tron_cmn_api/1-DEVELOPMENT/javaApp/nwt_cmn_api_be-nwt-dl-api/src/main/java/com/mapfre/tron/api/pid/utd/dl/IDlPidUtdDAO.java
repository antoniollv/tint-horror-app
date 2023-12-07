package com.mapfre.tron.api.pid.utd.dl;


import java.math.BigDecimal;
import java.util.Date;
import com.mapfre.nwt.trn.pid.utd.bo.OPidUtdS;

public interface IDlPidUtdDAO {
    
    OPidUtdS get(BigDecimal cmpVal, String claVal, String lngVal, Date vldDat, String usrLngVal);
    
    OPidUtdS get_A9990010(BigDecimal cmpVal, String claVal, String vldDat, String usrLngVal);
    
    OPidUtdS get_A9990010_UtdLng(BigDecimal cmpVal, String claVal, String vldDat, String usrLngVal);
    
}
