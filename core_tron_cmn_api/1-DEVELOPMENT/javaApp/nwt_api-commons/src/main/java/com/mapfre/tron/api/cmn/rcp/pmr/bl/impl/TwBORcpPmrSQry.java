package com.mapfre.tron.api.cmn.rcp.pmr.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.rcd.tsd.bo.ORcdTsdS;
import com.mapfre.nwt.trn.rcp.pmr.bo.ORcpPmrS;
import com.mapfre.tron.api.cmn.rcp.pmr.bl.IBlORcpPmrSQry;
import com.mapfre.tron.api.cmn.rcp.pmr.dl.ORcdTsdSPK;
import com.mapfre.tron.api.cmn.rcp.pmr.dl.ORcpPmrPK;
import com.mapfre.tron.api.cmn.rcp.pmr.dl.ORcpPmrSDao;

@Service
public class TwBORcpPmrSQry implements IBlORcpPmrSQry{

    @Autowired
    protected ORcpPmrSDao oRcpPmrSDao;
        
    @Override
    public ORcpPmrS getORcpPmrSTypNam(ORcpPmrPK pk) {

	return oRcpPmrSDao.getORcpPmrSTypNam(pk);
	
    }

    @Override
    public ORcpPmrS getORcpPmrSCrnNam(ORcpPmrPK build) {
	
	return oRcpPmrSDao.getORcpPmrSCrnNam(build);
	

    }

    @Override
    public List<ORcdTsdS> getORcdTsdSList() {
	return oRcpPmrSDao.getORcdTsdSList();

    }

    @Override
    public ORcdTsdS getORcdTsdS(ORcdTsdSPK oRcdTsdSPK) {
	return oRcpPmrSDao.getORcdTsdS(oRcdTsdSPK);

    }

}
