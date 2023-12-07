package com.mapfre.tron.api.lsf.lsf.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.lsf.lsf.bo.OLsfLsfS;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;

public interface IDlLsfLsfDAO {

    OLsfLsfS getCodRamo(BigDecimal cmpVal, BigDecimal lobVal, String usrLngVal);
    
    OLsfLsfS getCodCob(BigDecimal cmpVal, BigDecimal lobVal, BigDecimal cvrVal, String usrLngVal);

    OLsfLsfS getFilTypVal(BigDecimal cmpVal, String filTypVal, String usrLngVal);

    OTrnPrcS getNomPrg(String nomPrg, String usrLngVal, BigDecimal cmpVal, String msgVal);

    OLsfLsfS getDelVal(BigDecimal cmpVal, BigDecimal delVal, BigDecimal lobVal, String usrLngVal);

    OLsfLsfS getSblVal(BigDecimal cmpVal, BigDecimal delVal, BigDecimal sblVal, BigDecimal lobVal, String usrLngVal);

    OLsfLsfS getSugVal(BigDecimal cmpVal, String sugVal, String usrLngVal);

    OLsfLsfS getSubVal(BigDecimal cmpVal, String sugVal, String usrLngVal);
    
    OLsfLsfS getSubVal2(BigDecimal cmpVal, String sugVal, String subVal, String usrLngVal);
    
    OLsfLsfS getMoneda(BigDecimal cmpVal, BigDecimal crnVal, String usrLngVal, String msgVal);
}
