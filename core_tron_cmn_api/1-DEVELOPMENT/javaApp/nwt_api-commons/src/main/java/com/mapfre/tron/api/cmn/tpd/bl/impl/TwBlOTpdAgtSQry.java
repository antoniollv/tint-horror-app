package com.mapfre.tron.api.cmn.tpd.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.tpd.agt.bo.OTpdAgtS;
import com.mapfre.tron.api.cmn.tpd.bl.IBlThpTdpQry;
import com.mapfre.tron.api.cmn.tpd.dl.OTpdAgtSDao;
import com.mapfre.tron.api.cmn.tpd.dl.OTpdAgtSPK;

@Service
public class TwBlOTpdAgtSQry implements IBlThpTdpQry {

    @Autowired
    protected OTpdAgtSDao oTpdAgtSDao;
	
	@Override
	public List<OTpdAgtS> getAgt(OTpdAgtSPK build) {
		
		return oTpdAgtSDao.getAgt(build);
	}

	@Override
	public OTpdAgtS getAgentType(OTpdAgtSPK pk) {
	    
		return oTpdAgtSDao.getAgentType(pk);

	}

}
