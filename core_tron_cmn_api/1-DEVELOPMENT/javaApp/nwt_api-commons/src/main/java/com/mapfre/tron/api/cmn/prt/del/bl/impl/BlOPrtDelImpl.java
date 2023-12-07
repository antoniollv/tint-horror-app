package com.mapfre.tron.api.cmn.prt.del.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.prt.del.bo.OPrtDelS;
import com.mapfre.tron.api.cmn.prt.del.bl.IBlOPrtDel;
import com.mapfre.tron.api.cmn.prt.del.dl.IDlOPrtDelDao;
import com.mapfre.tron.api.cmn.prt.del.dl.OPrtDelPK;

/**
 * The OPrtDel service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 14:00:11
 *
 */
@Service
public class BlOPrtDelImpl implements IBlOPrtDel {

    /** The OPrtDel repository.*/
    @Autowired
    protected IDlOPrtDelDao iDlOPrtDelDao;

    /**
     * Obtener la información de un acuerdo por compañia.
     *
     * @param - > la clave primaria
     * @return -> la información de un acuerdo por compañia
     */
    @Override
    public OPrtDelS get(OPrtDelPK oPrtDelPK) {
        return iDlOPrtDelDao.get(oPrtDelPK);
    }

}
