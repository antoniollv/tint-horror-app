package com.mapfre.tron.api.cmn.ply.ina.bl.impl.tw;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaCPT;
import com.mapfre.tron.api.bo.TronWebAccess;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.ply.ina.bl.IBlPlyInaQry;
import com.mapfre.tron.api.cmn.ply.ina.dl.ISrPlyInaQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The common PlyInaQry implementation repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 15 dic. 2020 - 16:30:46
 *
 */
@Data
@Slf4j
@TronWebAccess
@EqualsAndHashCode(callSuper=false)
public class TwCmnBlPlyInaQryImpl extends TwBlCmnBase implements IBlPlyInaQry {

    /** The PlyIneQry repository.*/
    @Autowired
    protected ISrPlyInaQryDAO iSrPlyInaQryDAO;

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
    @Transactional("transactionManagerTwDl")
    public OPlyInaCPT plyTbl(final String lngVal, final String usrVal, final Integer cmpVal, final String plyVal,
            final Integer aplVal, final String pmOrgTypVal, final String pmGetNamTypVal) {

        log.info("Tronweb business logic implementation plyTbl have been called...");

        // reseting session
        resetSession();

        return iSrPlyInaQryDAO.plyTbl(BigDecimal.valueOf(cmpVal),
                                      plyVal,
                                      BigDecimal.valueOf(aplVal),
                                      pmOrgTypVal,
                                      lngVal,
                                      pmGetNamTypVal);

    }

}
