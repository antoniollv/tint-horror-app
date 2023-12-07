package com.mapfre.tron.api.cmn.pid.enr.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.pid.enr.bo.OPidEnrS;
import com.mapfre.tron.api.cmn.pid.enr.bl.IBlPidEnr;
import com.mapfre.tron.api.cmn.pid.enr.dl.IDlPidEnrDao;
import com.mapfre.tron.api.cmn.pid.enr.dl.OPidEnrPK;

/**
 * The PidEnr service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 9 Dec 2021 - 11:27:45
 *
 */
@Service
public class BlPidEnrImpl implements IBlPidEnr {

    /** The PidEnr repository.*/
    @Autowired
    protected IDlPidEnrDao iDlPidEnrDao;

    /**
     * Obtiene la información de un suplemento.
     *
     * @param oPidEnrPK -> La clave primaria
     * @return          -> La información de un suplemento
     */
    @Override
    public OPidEnrS get(final OPidEnrPK oPidEnrPK) {
        return iDlPidEnrDao.get(oPidEnrPK);
    }

}
