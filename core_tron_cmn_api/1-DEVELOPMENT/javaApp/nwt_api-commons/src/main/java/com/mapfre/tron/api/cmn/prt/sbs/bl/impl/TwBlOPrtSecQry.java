package com.mapfre.tron.api.cmn.prt.sbs.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.prt.sec.bo.OPrtSecS;
import com.mapfre.tron.api.cmn.prt.sec.bl.IBlOPrtSecQry;
import com.mapfre.tron.api.cmn.prt.sec.dl.OPrtSecDao;
import com.mapfre.tron.api.cmn.prt.sec.dl.OPrtSecPK;

@Service
public class TwBlOPrtSecQry implements IBlOPrtSecQry {

    @Autowired
    protected OPrtSecDao oPrtSecDao;
    
    @Override
    public OPrtSecS getSecNam(OPrtSecPK pk) {
	
    	return oPrtSecDao.get(pk);
    }

	@Override
	public List<OPrtSecS> getSecList(OPrtSecPK pk) {
		
		return oPrtSecDao.getSecList(pk);
	}
}
