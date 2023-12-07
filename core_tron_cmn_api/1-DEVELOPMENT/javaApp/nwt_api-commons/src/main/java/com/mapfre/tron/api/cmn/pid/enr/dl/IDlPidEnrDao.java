package com.mapfre.tron.api.cmn.pid.enr.dl;

import com.mapfre.nwt.trn.pid.enr.bo.OPidEnrS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The PidEnr repository interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 10:33:12
 *
 */
public interface IDlPidEnrDao extends NewtronDao<OPidEnrS, OPidEnrPK> {

    /**
     * Obtiene la información de un suplemento.
     *
     * @param oPidEnrPK -> La clave primaria
     * @return          -> La información de un suplemento
     */
    OPidEnrS get(OPidEnrPK oPidEnrPK);

}
