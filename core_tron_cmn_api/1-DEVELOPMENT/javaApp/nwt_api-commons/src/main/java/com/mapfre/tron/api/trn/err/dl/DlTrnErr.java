package com.mapfre.tron.api.trn.err.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

public interface DlTrnErr {
    
    public OTrnErrS getError(BigDecimal codMensaje, String lngVal, String temVal, String prpIdn, BigDecimal cmpVal);
    public OTrnErrS getErrorWithoutPrpIdn(BigDecimal codMensaje, String lngVal, String temVal, BigDecimal cmpVal);
    public OTrnErrS getErrorWithoutTemVal(BigDecimal codMensaje, String lngVal, String prpIdn, BigDecimal cmpVal);
    public OTrnErrS getError(BigDecimal codMensaje, String lngVal, BigDecimal cmpVal);
}
