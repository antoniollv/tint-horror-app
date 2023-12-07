package com.mapfre.tron.api.thp.cmc.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.thp.cmc.bo.OThpCmcS;

public interface IDlThpCmcDAO {
    
    //Consultar Cuadro comision
    OThpCmcS getCodCuaCom(BigDecimal cmpVal, BigDecimal cmcVal, String usrLngVal);
    
    //Consultar Ramo
    OThpCmcS getCodRamo(BigDecimal cmpVal, BigDecimal lobVal, String usrLngVal);
    
    //Consultar Cobertura
    OThpCmcS getCodCob(BigDecimal cmpVal, BigDecimal lobVal, BigDecimal cvrVal, String usrLngVal);
    
    //Consultar Modalidad
    OThpCmcS getCodMod(BigDecimal cmpVal, BigDecimal mdtVal, String usrLngVal);
    
    //Consultar Agente
    OThpCmcS getCodAgente(BigDecimal cmpVal, BigDecimal agnVal, String usrLngVal);
    
    //Consultar nivel1
    OThpCmcS getCodNivel1(BigDecimal cmpVal, String frsLvlVal, String usrLngVal);
    
    //Consultar nivel2
    OThpCmcS getCodNivel2(BigDecimal cmpVal, String scnLvlVal, String usrLngVal);
    
    //Consultar nivel3
    OThpCmcS getCodNivel3(BigDecimal cmpVal, String thrLvlVal, String usrLngVal);
    
    //Consultar canal1
    OThpCmcS getCodCanal1(BigDecimal cmpVal, String frsDstHnlVal, String usrLngVal);
    
    //Consultar canal2
    OThpCmcS getCodCanal2(BigDecimal cmpVal, String scnDstHnlVal, String usrLngVal);
    
    //Consultar canal3
    OThpCmcS getCodCanal3(BigDecimal cmpVal, String thrDstHnlVal, String usrLngVal);

}
