package com.mapfre.tron.api.cmn.lsf.fid.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.lsf.fid.bo.OLsfFidS;
import com.mapfre.tron.api.cmn.lsf.fid.bl.IBlOLsfFid;
import com.mapfre.tron.api.cmn.lsf.fid.dl.IDlOLsfFid;

/**
 * The IBlOLsfFid business service implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 9 feb 2023 - 17:49:28
 *
 */
@Service
public class BlOLsfFidImpl implements IBlOLsfFid {

    @Autowired
    protected IDlOLsfFid iDlOLsfFid;

    /**
     * Get the OLsfFidS
     *
     * @param map    -> The map with the query params values
     * @return       -> The OLsfFidS
     */
    @Override
    public OLsfFidS getOLsfFid(Map<String, Object> map) {
        return iDlOLsfFid.getOLsfFid(map);
    }

}
