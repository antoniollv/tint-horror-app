package com.mapfre.tron.api.cmn.prt.sec.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;
import com.mapfre.tron.api.cmn.prt.sbs.bl.IBlOPrtSbsQry;
import com.mapfre.tron.api.cmn.prt.sbs.dl.OPrtSbsDao;
import com.mapfre.tron.api.cmn.prt.sbs.dl.OPrtSbsPK;
import com.mapfre.tron.api.cmn.prt.sbs.dl.OPrtSbsPK2;

@Service
public class TwBlOPrtSbsQry implements IBlOPrtSbsQry {

    @Autowired
    protected OPrtSbsDao oPrtSbsDao;

    @Override
    public List<OPrtSbsS> getSubSectorList(OPrtSbsPK build) {

	return oPrtSbsDao.getSubSectorList(build);
    }
    
    
    @Override
    public OPrtSbsS getSubSector(OPrtSbsPK2 build) {

    	return oPrtSbsDao.getSubSector(build);
    }
    

}
