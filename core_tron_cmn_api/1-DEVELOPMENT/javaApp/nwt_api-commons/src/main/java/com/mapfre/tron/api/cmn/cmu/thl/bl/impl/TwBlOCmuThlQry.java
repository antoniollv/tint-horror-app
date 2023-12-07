package com.mapfre.tron.api.cmn.cmu.thl.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.tron.api.cmn.cmu.thl.bl.IBlOCmuThlQry;
import com.mapfre.tron.api.cmn.cmu.thl.dl.OCmuThlDao;
import com.mapfre.tron.api.cmn.cmu.thl.dl.OCmuThlPK;

@Service
public class TwBlOCmuThlQry implements IBlOCmuThlQry {

    @Autowired
    protected OCmuThlDao oCmuThlDao;
    
    @Override
    public OCmuThlS getCptThrLvlNam(OCmuThlPK pk) {
	
	 return oCmuThlDao.get(pk);
    }
}

