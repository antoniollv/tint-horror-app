package com.mapfre.tron.api.cmc.agn.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.thp.cmc.bo.OThpCmcS;

public interface IDlThpCmcAgnDAO {
    
    //Consultar codigo tratamiento
    OThpCmcS getCodTratamiento(BigDecimal cmpVal, String pocVal, String usrLngVal);
    
}
