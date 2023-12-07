package com.mapfre.tron.api.trn.nod.dl;


import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.nwo.bo.OTrnNwoS;


public interface IDlTrnNodDAO {

        OTrnNwoS getInstalacion(BigDecimal cmpVal, String insVal, String usrLngVal);
}
