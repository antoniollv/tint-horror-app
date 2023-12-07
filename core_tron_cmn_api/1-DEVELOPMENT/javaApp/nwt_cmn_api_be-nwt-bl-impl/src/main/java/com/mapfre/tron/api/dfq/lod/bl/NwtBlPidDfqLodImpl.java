package com.mapfre.tron.api.dfq.lod.bl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.trn.pid.dfq.bo.OPidDfqP;
import com.mapfre.nwt.trn.pid.dfq.sr.ISrPidDfqCrt;
import com.mapfre.nwt.trn.trn.cnx.bo.OTrnCnxS;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;
import com.mapfre.tron.api.bo.NewTronAccess;
import com.mapfre.tron.api.cmn.IssueCommonServices;
import com.mapfre.tron.api.cmn.ResetPacakge;
import com.mapfre.tron.api.pid.dfq.bl.IBlPidDfqCue;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@NewTronAccess
public class NwtBlPidDfqLodImpl implements IBlPidDfqLod {

    @Autowired
    protected IBlPidDfqCue iBlPidDfqCue;
    
    @Autowired
    protected ISrPidDfqCrt iSrPidDfqCrt;
    
    /** Servicio común para inicializar el contexto. */
    @Autowired
    protected IssueCommonServices iCSrv;

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
    
    @Override
    public void savBtc(String lngVal, String usrVal, Integer cmpVal) throws Exception {
	
	log.info("Newtron business logic implementation savBtc have been called...");       
        // reseting session
        resetSession();
        
        // Se inicializa el contexto lógico y las variables globales
        OTrnCnxS pmOTrnCnxS = iCSrv.getCnxSrv(lngVal, new BigDecimal(cmpVal), usrVal);
        
        // ISD 314 - OFRECER HALLAR cotización diaria fondo proveedor
        List<OPidDfqP> lvOPidDfqPT = iBlPidDfqCue.savSle(pmOTrnCnxS);
        
        // ISD 306 - OFRECER HALLAR cotización diaria fondo
        OTrnPrcS lvOTrnPrcS = iSrPidDfqCrt.sav(pmOTrnCnxS.getCmpVal(), lvOPidDfqPT, pmOTrnCnxS);
        
        //Revisamos el resultado
        getNwtUtils().isTrmOk(lvOTrnPrcS , "ISrPidDfqLod.savBtc");
    }

}
