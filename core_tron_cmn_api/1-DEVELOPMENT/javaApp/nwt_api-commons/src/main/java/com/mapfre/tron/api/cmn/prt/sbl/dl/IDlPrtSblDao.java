package com.mapfre.tron.api.cmn.prt.sbl.dl;

import com.mapfre.nwt.trn.prt.sbl.bo.OPrtSblS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The PrtSbl interface repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 07:41:25
 *
 */
public interface IDlPrtSblDao extends NewtronDao<OPrtSblS, OPrtSblPK> {

    /**
     * Obtener la información del subacuerdo por compañía.
     *
     * @param  -> la clave primaria
     * @return -> La información del subacuerdo por compañía
     */
    OPrtSblS get(OPrtSblPK oPrtSblPK);

}
