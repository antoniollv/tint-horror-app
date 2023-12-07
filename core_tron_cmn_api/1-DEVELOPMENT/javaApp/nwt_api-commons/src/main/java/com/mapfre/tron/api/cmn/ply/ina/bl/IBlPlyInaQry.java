package com.mapfre.tron.api.cmn.ply.ina.bl;

import com.mapfre.nwt.trn.ply.ina.bo.OPlyInaCPT;

/**
 * The business logic PlyInaQry interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @vesion 15 dic. 2020 - 12:27:43
 *
 */
public interface IBlPlyInaQry {

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
    OPlyInaCPT plyTbl(String lngVal, 
                      String usrVal, 
                      Integer cmpVal, 
                      String plyVal, 
                      Integer aplVal, 
                      String pmOrgTypVal,
                      String pmGetNamTypVal);

}
