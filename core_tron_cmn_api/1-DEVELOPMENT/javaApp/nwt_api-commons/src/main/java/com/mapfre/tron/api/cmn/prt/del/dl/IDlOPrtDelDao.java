package com.mapfre.tron.api.cmn.prt.del.dl;

import com.mapfre.nwt.trn.prt.del.bo.OPrtDelS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The PrtDel repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 13:00:16
 *
 */
public interface IDlOPrtDelDao extends NewtronDao<OPrtDelS, OPrtDelPK> {

    /**
     * Obtener la información de un acuerdo por compañia.
     *
     * @param - > la clave primaria
     * @return -> la información de un acuerdo por compañia
     */
    OPrtDelS get(OPrtDelPK oPrtDelPK);

}
