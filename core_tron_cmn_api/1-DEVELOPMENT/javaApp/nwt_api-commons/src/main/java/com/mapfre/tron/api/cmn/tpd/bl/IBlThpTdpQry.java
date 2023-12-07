package com.mapfre.tron.api.cmn.tpd.bl;

import java.util.List;

import com.mapfre.nwt.trn.tpd.agt.bo.OTpdAgtS;
import com.mapfre.tron.api.cmn.tpd.dl.OTpdAgtSPK;

public interface IBlThpTdpQry {
	
	public List<OTpdAgtS> getAgt(OTpdAgtSPK build);

	public OTpdAgtS getAgentType(OTpdAgtSPK build);

}
