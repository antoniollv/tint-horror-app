package com.mapfre.tron.api.cmn.pid.enr.bl;

import com.mapfre.nwt.trn.pid.enr.bo.OPidEnrS;
import com.mapfre.tron.api.cmn.pid.enr.dl.OPidEnrPK;

/**
 * The PidEnr service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 11:26:12
 *
 */
public interface IBlPidEnr {

    /**
     * Obtiene la información de un suplemento.
     *
     * @param oPidEnrPK -> La clave primaria
     * @return          -> La información de un suplemento
     */
    OPidEnrS get(OPidEnrPK oPidEnrPK);

}
