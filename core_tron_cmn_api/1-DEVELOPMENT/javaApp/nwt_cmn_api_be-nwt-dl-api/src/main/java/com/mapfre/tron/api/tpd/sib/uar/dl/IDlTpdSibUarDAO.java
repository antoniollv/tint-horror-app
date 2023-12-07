package com.mapfre.tron.api.tpd.sib.uar.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.tpd.sib.bo.OTpdSibS;

public interface IDlTpdSibUarDAO {
    
    OTpdSibS get_Usr(BigDecimal cmpVal, String cmpUsrVal,  String usrLngVal);
    
}
