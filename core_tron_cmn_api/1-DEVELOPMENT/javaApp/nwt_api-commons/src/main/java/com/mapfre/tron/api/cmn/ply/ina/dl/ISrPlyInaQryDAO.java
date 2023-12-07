package com.mapfre.tron.api.cmn.ply.ina.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaCPT;

/**
 * The PlyInaQry interface repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 15 dic. 2020 - 16:42:44
 *
 */
public interface ISrPlyInaQryDAO {

    /**
     * plyTbl: ISU-25153 - OFRECER CONSULTAR intervencion agente por poliza, vigente, conjunto.
     *
     * arquitectura - pvraul1
     * 
     * @param  pmCmpVal        -> Company code
     * @param  pmPlyVal        -> Policy
     * @param  pmAplVal        -> Application
     * @param  pmOrgTypVal     -> Origen
     * @param  pmLngVal        -> Language code
     * @param  pmGetNamTypVal  -> Mostrar nombre
     * @return OPlyInaCPT      -> Agent Intervention List
     */
    OPlyInaCPT plyTbl(BigDecimal pmCmpVal, String pmPlyVal, BigDecimal pmAplVal, String pmOrgTypVal, String pmLngVal,
                             String pmGetNamTypVal);

}
