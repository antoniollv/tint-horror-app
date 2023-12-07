package com.mapfre.tron.api.pgm.dl;

import java.math.BigDecimal;
import com.mapfre.nwt.trn.trn.pgm.bo.OTrnPgmS;

public interface IDlPgmDAO {
    
    // Obtener programa
    OTrnPgmS get_pgmVal(BigDecimal cmpVal, String pgmVal, String usrLngVal);
    
    // Obtener app_val
    OTrnPgmS get_pgmAppVal(BigDecimal cmpVal, String pgmVal, String usrLngVal) ;
    
    // Obtener opr_ind_val
    OTrnPgmS get_oprIdnVal(BigDecimal cmpVal, String pgmVal, String usrLngVal) ;
}
