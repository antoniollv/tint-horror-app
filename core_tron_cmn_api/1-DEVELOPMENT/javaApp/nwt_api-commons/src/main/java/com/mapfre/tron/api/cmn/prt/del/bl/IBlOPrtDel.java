package com.mapfre.tron.api.cmn.prt.del.bl;

import com.mapfre.nwt.trn.prt.del.bo.OPrtDelS;
import com.mapfre.tron.api.cmn.prt.del.dl.OPrtDelPK;

/**
 * The OPrtDel service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 13:56:18
 *
 */
public interface IBlOPrtDel {

    /**
     * Obtener la información de un acuerdo por compañia.
     *
     * @param - > la clave primaria
     * @return -> la información de un acuerdo por compañia
     */
    OPrtDelS get(OPrtDelPK oPrtDelPK);

}
