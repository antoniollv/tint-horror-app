package com.mapfre.tron.api.thp.acv.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.nwt.trn.thp.acv.bo.OThpAcvS;
import com.mapfre.nwt.trn.thp.bno.bo.OThpBnoS;

public interface IDlThpAcvDAO2 {
    
    //Consultar AG (A1001390 COD_ACT_TERCERO=2)
    OThpAcvS get_AG(BigDecimal cmpVal, String mnrVal, String usrLngVal);
    
    //Consultar CI, CO, CP (A1001390 COD_ACT_TERCERO=12)
    OThpAcvS get_CICOCP(BigDecimal cmpVal, String mnrVal, String usrLngVal);
    
    //Consultar GP (A1001390 COD_ACT_TERCERO=13)
    OThpAcvS get_GP(BigDecimal cmpVal, String mnrVal, String usrLngVal);
    
    //Consultar BA, DB, TA (Bno/2)
    OThpBnoS get_BADBTA(BigDecimal cmpVal, String mnrVal, String usrLngVal);
    
    //Consultar GD, OF (Thl/2)
    OCmuThlS get_GDOF(BigDecimal cmpVal, String mnrVal, String usrLngVal);
    
}
