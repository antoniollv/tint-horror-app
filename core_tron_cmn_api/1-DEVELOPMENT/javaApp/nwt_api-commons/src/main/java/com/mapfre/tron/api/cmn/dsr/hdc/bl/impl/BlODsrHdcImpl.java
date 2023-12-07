package com.mapfre.tron.api.cmn.dsr.hdc.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.dsr.hdc.bo.ODsrHdcS;
import com.mapfre.tron.api.cmn.dsr.hdc.bl.IBlODsrHdc;
import com.mapfre.tron.api.cmn.dsr.hdc.dl.ODsrHdcDao;
import com.mapfre.tron.api.cmn.dsr.hdc.dl.ODsrHdcPK;

/**
 * The DsrHdc service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 7 Dec 2021 - 11:14:37
 *
 */
@Service
public class BlODsrHdcImpl implements IBlODsrHdc {

    /** The DsrHdc repository.*/
    @Autowired
    protected ODsrHdcDao oDsrHdcDao;

    /**
     * Get ODsrHdcS by PK.
     *
     * @param o -> The PK
     * @return  -> The ODsrHdcS
     */
    @Override
    public ODsrHdcS get(ODsrHdcPK o) {
        return oDsrHdcDao.get(o);
    }

}
