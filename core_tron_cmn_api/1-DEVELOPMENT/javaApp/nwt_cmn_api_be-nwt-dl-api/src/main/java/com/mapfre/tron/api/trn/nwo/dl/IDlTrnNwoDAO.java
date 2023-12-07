package com.mapfre.tron.api.trn.nwo.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.trn.nwo.bo.OTrnNwoS;


public interface IDlTrnNwoDAO {

    OTrnNwoS getRamo(BigDecimal cmpVal, BigDecimal lobVal, String usrLngVal);
    
    OTrnNwoS getUsuario(BigDecimal cmpVal, String acsUsrVal, String usrLngVal);
    
    OTrnNwoS getInstalacion(BigDecimal cmpVal, String insVal, String usrLngVal);
    
    OTrnNwoS getOperacion(BigDecimal cmpVal, String oprIdnVal, String usrLngVal);
}
