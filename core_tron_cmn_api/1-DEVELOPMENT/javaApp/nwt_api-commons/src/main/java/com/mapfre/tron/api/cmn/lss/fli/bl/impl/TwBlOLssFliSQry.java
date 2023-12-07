package com.mapfre.tron.api.cmn.lss.fli.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.lss.fli.bo.OLssFliS;
import com.mapfre.tron.api.cmn.lss.fli.bl.IBlOLssFliSQry;
import com.mapfre.tron.api.cmn.lss.fli.dl.OLssFliDao;
import com.mapfre.tron.api.cmn.lss.fli.dl.OLssFliPK;

@Service
public class TwBlOLssFliSQry implements IBlOLssFliSQry{

    @Autowired
    protected OLssFliDao oLssFliDao;
        
    @Override
    public OLssFliS getOLssFliSTypNam(OLssFliPK pk) {

	return  oLssFliDao.getOLssFliSTypNam(pk);

    }

}
