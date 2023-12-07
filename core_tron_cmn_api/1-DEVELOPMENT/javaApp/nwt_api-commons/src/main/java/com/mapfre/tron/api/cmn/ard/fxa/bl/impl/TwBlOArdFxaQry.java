package com.mapfre.tron.api.cmn.ard.fxa.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.ard.fxa.bo.OArdFxaS;
import com.mapfre.tron.api.cmn.ard.fxa.bl.IBlArdFxaQry;
import com.mapfre.tron.api.cmn.ard.fxa.dl.OArdFxaDao;
import com.mapfre.tron.api.cmn.ard.fxa.dl.OArdFxaPK;

/**
 * The fixed attributes controller.
 *
 * @author arquitectura - izhan del rio
 * @since 1.8
 * @version 12 Nov 2021 - 16:17:02
 *
 */
@Service
public class TwBlOArdFxaQry implements IBlArdFxaQry {

    @Autowired
    protected OArdFxaDao oArdFxaDao;

    @Override
    public List<OArdFxaS> getListOArdFxaS(OArdFxaPK pk) {
        return oArdFxaDao.getAllWithPK(pk);
    }

}
