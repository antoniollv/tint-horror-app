package com.mapfre.tron.api.cmn.prt.sbl.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.prt.sbl.bo.OPrtSblS;
import com.mapfre.tron.api.cmn.prt.sbl.bl.IBlPrtSbl;
import com.mapfre.tron.api.cmn.prt.sbl.dl.IDlPrtSblDao;
import com.mapfre.tron.api.cmn.prt.sbl.dl.OPrtSblPK;

/**
 * The PrtSbl service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 08:03:28
 *
 */
@Service
public class BlPrtSblImpl implements IBlPrtSbl {

    /** The PrtSbl repository.*/
    @Autowired
    protected IDlPrtSblDao iDlPrtSblDao;

    /**
     * Obtener la información del subacuerdo por compañía.
     *
     * @param  -> la clave primaria
     * @return -> La información del subacuerdo por compañía
     */
    @Override
    public OPrtSblS get(final OPrtSblPK oPrtSblPK) {
        return iDlPrtSblDao.get(oPrtSblPK);
    }

}
