package com.mapfre.tron.api.cmn.ply.ina.bl.impl.nwt;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaCPT;
import com.mapfre.nwt.trn.ply.ina.sr.ISrPlyInaQry;
import com.mapfre.tron.api.bo.NewTronAccess;
import com.mapfre.tron.api.cmn.ResetPacakge;
import com.mapfre.tron.api.cmn.ply.ina.bl.IBlPlyInaQry;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The business logic PlyInaQry common implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 15 dic. 2020 - 12:34:56
 *
 */
@Data
@Slf4j
@NewTronAccess
public class NwtCmnBlPlyInaQryImpl implements IBlPlyInaQry {

    /** The PlyInaQry gaia service.*/
    @Autowired
    private ISrPlyInaQry iSrPlyInaQry;

    /** The resetPackage property.*/
    @Autowired
    private ResetPacakge resetPackage;

    /** The newtron utils. */
    private NwtUtils nwtUtils;

    /**
     * plyTbl: ISU-25153 - OFRECER CONSULTAR intervencion agente por poliza, vigente, conjunto.
     *
     * @author arquitectura - pvraul1
     * 
     * @param  lngVal          -> Language code
     * @param  usrVal          -> User code
     * @param  cmpVal          -> Company code
     * @param  plyVal          -> Policy
     * @param  aplVal          -> Application
     * @param  pmOrgTypVal     -> Origen
     * @param  pmGetNamTypVal  -> Mostrar nombre
     *
     * @return OPlyInaCPT      -> Agent Intervention List
     */
    @Override
    public OPlyInaCPT plyTbl(final String lngVal, final String usrVal, final Integer cmpVal, final String plyVal,
            final Integer aplVal, final String pmOrgTypVal, final String pmGetNamTypVal) {

        log.info("Newtron business logic implementation plyTbl has been called...");

        // reseting session
        resetSession();

        OPlyInaCPT oPlyInaCPT = iSrPlyInaQry.plyTbl(BigDecimal.valueOf(cmpVal),
                                                    plyVal,
                                                    BigDecimal.valueOf(aplVal),
                                                    pmOrgTypVal,
                                                    lngVal,
                                                    pmGetNamTypVal);

        getNwtUtils().isTrmOk(oPlyInaCPT, "iSrPlyInaQry.plyTbl");

        return oPlyInaCPT;
    }

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
    
    /** Reset the session.*/
    protected void resetSession() {
        log.debug("Reseting session...");
        resetPackage.executeRP();
        log.debug("The session has been reset.");
    }

}
