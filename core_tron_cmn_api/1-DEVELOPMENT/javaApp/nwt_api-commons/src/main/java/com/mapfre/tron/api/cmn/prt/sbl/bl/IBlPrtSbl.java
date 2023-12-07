package com.mapfre.tron.api.cmn.prt.sbl.bl;

import com.mapfre.nwt.trn.prt.sbl.bo.OPrtSblS;
import com.mapfre.tron.api.cmn.prt.sbl.dl.OPrtSblPK;

/**
 * The PrtSbl service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 08:02:10
 *
 */
public interface IBlPrtSbl {

    /**
     * Obtener la información del subacuerdo por compañía.
     *
     * @param  -> la clave primaria
     * @return -> La información del subacuerdo por compañía
     */
    OPrtSblS get(OPrtSblPK oPrtSblPK);

}
