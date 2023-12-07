package com.mapfre.tron.api.cmn.tpd.dcy.bl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.tpd.dcy.bo.OTpdDcyS;
import com.mapfre.nwt.trn.tpd.ssd.bo.OTpdSsdS;
import com.mapfre.tron.api.cmn.tpd.dcy.bl.IBlOTpdDcyS;
import com.mapfre.tron.api.cmn.tpd.dcy.dl.IDlOTpdDcyS;


@Service
public class IBlOTpdDcySImpl implements IBlOTpdDcyS {

	@Autowired
	protected IDlOTpdDcyS iDlOTpdDcyS;
	
	@Override
	public OTpdDcyS getOTpdDcyS(Map<String, Object> map) {
		return iDlOTpdDcyS.getOTpdDcyS(map);
	}
	
	
	@Override
	public OTpdSsdS getOTpdSsdS(Map<String, Object> map) {
		return iDlOTpdDcyS.getOTpdSsdS(map);
	}

}
