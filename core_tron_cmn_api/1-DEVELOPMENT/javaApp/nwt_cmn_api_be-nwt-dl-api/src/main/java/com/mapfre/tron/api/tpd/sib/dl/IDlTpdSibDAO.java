package com.mapfre.tron.api.tpd.sib.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.tpd.sib.bo.OTpdSibS;

public interface IDlTpdSibDAO {
    
    OTpdSibS get_Rol(BigDecimal cmpVal, String rolVal, String usrLngVal);
    
}
