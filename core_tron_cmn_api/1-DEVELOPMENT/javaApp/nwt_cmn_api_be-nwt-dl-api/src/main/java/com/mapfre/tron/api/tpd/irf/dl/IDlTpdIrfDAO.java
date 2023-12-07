package com.mapfre.tron.api.tpd.irf.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.tpd.irf.bo.OTpdIrfS;


public interface IDlTpdIrfDAO {

    OTpdIrfS getTipoGestion(BigDecimal cmpVal, String mngTypVal, String usrLngVal);

    OTpdIrfS getTipoMovimiento(BigDecimal cmpVal, String iccMvmTypVal, String usrLngVal);

    OTpdIrfS getMotivo(BigDecimal cmpVal, String iccRsnVal, String usrLngVal);


}
