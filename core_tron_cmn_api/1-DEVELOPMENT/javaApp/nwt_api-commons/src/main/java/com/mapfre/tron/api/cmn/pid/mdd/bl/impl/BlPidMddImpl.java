package com.mapfre.tron.api.cmn.pid.mdd.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.tron.api.cmn.pid.mdd.bl.IBlPidMdd;
import com.mapfre.tron.api.cmn.pid.mdd.dl.IDlPidMddDao;

/**
 * The PidMdd business implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 19 May 2022 - 12:30:38
 *
 */
@Service
public class BlPidMddImpl implements IBlPidMdd {

    @Autowired
    protected IDlPidMddDao iDlPidMddDao;

    /**
     * Get the description.
     *
     * @param map 
     * @return       -> The description
     */
    @Override
    public String getMdtNam(final Map<String, Object> map) {
        return iDlPidMddDao.getMdtNam(map);
    }

}
