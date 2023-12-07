package com.mapfre.tron.api.cmn;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;
import com.mapfre.nwt.trn.trn.cnx.sr.ISrTrnCnxCue;

/**
 * Agrupación de funcionalidades comunes reutilizadas en diferentes clases.
 * 
 * @author fjlorente
 */
@Component
public final class CmnCommonServices {

    private ISrTrnCnxCue srTrnCnxCue;

    /**
     * Create a new instance of the class.
     * @param srTrnCnxCue
     */
    @Autowired
    protected CmnCommonServices(ISrTrnCnxCue srTrnCnxCue) {
        super();
        this.srTrnCnxCue = srTrnCnxCue;
    }

    //------------------------------------------------------------------------------
    // servicio recuperar contexto 
    //------------------------------------------------------------------------------
    public OTrnCnxS getCnxSrv(String pmLngVal, 
                           BigDecimal pmCmpVal, 
                           String pmUsrVal) {
        // CNX
        OTrnCnxS cnx = new OTrnCnxS();
        String sesionId = UUID.randomUUID().toString();
        cnx.setPrcTypVal(CTrn.PRC_TYP_ONL);
        cnx.setSesVal(sesionId);
        cnx.setCmpVal(pmCmpVal);
        cnx.setUsrVal(pmUsrVal);
        cnx.setLngVal(pmLngVal);
        // NFU-431 - OFRECER HALLAR contexto, datos basicos
        cnx = srTrnCnxCue.daaBsc(cnx.getUsrVal(), 
                                 cnx.getCmpVal(), 
                                 cnx);
        return cnx;
    }

}
