package com.mapfre.tron.api.pfc.lod.bl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.pid.ivf.sr.ISrPidIvfCrt;
import com.mapfre.nwt.trn.pid.pfc.sr.ISrPidPfcCrt;
import com.mapfre.nwt.trn.pid.pfm.sr.ISrPidPfmCrt;
import com.mapfre.nwt.trn.pid.psp.sr.ISrPidPspCrt;
import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;
import com.mapfre.tron.api.bo.NewTronAccess;
import com.mapfre.tron.api.cmn.IssueCommonServices;
import com.mapfre.tron.api.cmn.ResetPacakge;
import com.mapfre.tron.api.pid.pfc.bl.IBlPidPfcCue;
import com.mapfre.tron.api.pid.pfc.bl.OPidPfcEitDto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * NwtBlPidPfcLodImpl
 *
 * @author magarafr
 * @since 1.8
 * @version 25 ene. 2021 12:54:02
 *
 */
@Data
@Slf4j
@NewTronAccess
public class NwtBlPidPfcLodImpl implements IBlPidPfcLod {

    /** Servicio común para inicializar el contexto. */
    @Autowired
    protected IssueCommonServices iCSrv;
    
    @Autowired
    protected IBlPidPfcCue iBlPidPfcCue;
    
    @Autowired
    protected ISrPidIvfCrt iSrPidIvfCrt;
    
    @Autowired
    protected ISrPidPfcCrt iSrPidPfcCrt;
    
    @Autowired
    protected ISrPidPspCrt iSrPidPspCrt;
    
    @Autowired
    protected ISrPidPfmCrt iSrPidPfmCrt;
    
    /** The resetPackage property. */
    @Autowired
    protected ResetPacakge resetPackage;

    /** The newtron utils. */
    protected NwtUtils nwtUtils;
    

    /**
     * Get the utils property.
     * 
     * @return the utils property
     */
    protected NwtUtils getNwtUtils() {
        if (nwtUtils == null) {
            nwtUtils = new NwtUtils();
        }
        return nwtUtils;
    }

    /** Reset the session. */
    protected void resetSession() {
        log.debug("Reseting session...");
        resetPackage.executeRP();
        log.debug("The session has been reset.");
    }
    
    /**
     * savPffBtc : AMBT-87 - Save portfolio fund composition
     *
     * @author magarafr
     * @purpose Save portfolio fund composition. It will be mandatory send the
     *          parameters defined in the service and the service will response with
     *          the output object defined.
     * 
     * @param lngVal -> Idioma
     * @param usrVal -> usuario
     * @param cmpVal -> Compañia
     *
     * @return -> Registro de movimiento
     * @throws Exception 
     */
    @Override
    public void savPffBtc(String lngVal, String usrVal, Integer cmpVal) throws Exception {

	// Se inicializa el contexto lógico y las variables globales
	OTrnCnxS pmOTrnCnxS= iCSrv.getCnxSrv(lngVal, new BigDecimal(cmpVal), usrVal);
	
	    OPidPfcEitDto lvOPidPfcEitDto = iBlPidPfcCue.getPfcSle(pmOTrnCnxS);
		
	    // Si fondo inversión conjunto tiene valor
	    if (lvOPidPfcEitDto.getOPidIvfPT()!=null && !lvOPidPfcEitDto.getOPidIvfPT().isEmpty()){
		// ISD-3039 - OFRECER CREAR fondo inversión definición
        	OTrnPrcS lvOTrnPrcIvfS = iSrPidIvfCrt.sav(lvOPidPfcEitDto.getOPidIvfPT(), pmOTrnCnxS);
        	    // Si no hay errores
        	    if (lvOTrnPrcIvfS.getTrmVal().equals(CTrn.TRM_VAL_OK)) {
        		// Si cesta fondo composicion conjunto tiene valor
        		if (lvOPidPfcEitDto.getOPidPfcPT()!=null && !lvOPidPfcEitDto.getOPidPfcPT().isEmpty()){
        		    //ISD-3042 - OFRECER CREAR cesta fondo composicion
        		    OTrnPrcS lvOTrnPrcPfcS = iSrPidPfcCrt.sav(lvOPidPfcEitDto.getOPidPfcPT(),pmOTrnCnxS);
        		    //Revisamos el resultado
        		    getNwtUtils().isTrmOk(lvOTrnPrcPfcS , "IsrPidPfcCrt.sav"); 
        		}// Fin [Si cesta fondo composicion conjunto tiene valor]
        		// Si cesta fondo distribucion conjunto tiene valor
        		if (lvOPidPfcEitDto.getOPidPspPT()!=null && !lvOPidPfcEitDto.getOPidPspPT().isEmpty()){
        		    //ISD-3042 - OFRECER CREAR cesta fondo composicion
        		    OTrnPrcS lvOTrnPrcPspS = iSrPidPspCrt.sav(lvOPidPfcEitDto.getOPidPspPT(),pmOTrnCnxS);
        		    //Revisamos el resultado
        		    getNwtUtils().isTrmOk(lvOTrnPrcPspS , "IsrPidPspCrt.sav"); 
        		}// Fin [Si cesta fondo distribucion conjunto tiene valor]
        		// Si cesta fondo documento conjunto tiene valor
        		irPidPfmCrtSav(pmOTrnCnxS, lvOPidPfcEitDto);// Fin [Si cesta fondo documento conjunto tiene valor] 
        	    }// Fin [Si no hay errores] 
        	}// Fin [Si fondo inversión conjunto tiene valor]
    }

    protected void irPidPfmCrtSav(OTrnCnxS pmOTrnCnxS, OPidPfcEitDto lvOPidPfcEitDto) {
	if (lvOPidPfcEitDto.getOPidPfmPT()!=null && !lvOPidPfcEitDto.getOPidPfmPT().isEmpty()){
	    //ISD-3048 - OFRECER CREAR cesta fondo documento conjunto
	    OTrnPrcS lvOTrnPrcPfmS = iSrPidPfmCrt.sav(lvOPidPfcEitDto.getOPidPfmPT(),pmOTrnCnxS);
	    //Revisamos el resultado
	    getNwtUtils().isTrmOk(lvOTrnPrcPfmS , "IsrPidPfmCrt.sav");
	}
    }

}
