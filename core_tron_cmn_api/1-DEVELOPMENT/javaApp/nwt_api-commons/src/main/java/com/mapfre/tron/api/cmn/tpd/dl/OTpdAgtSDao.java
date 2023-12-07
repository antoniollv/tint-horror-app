package com.mapfre.tron.api.cmn.tpd.dl;

import java.util.List;

import com.mapfre.nwt.trn.tpd.agt.bo.OTpdAgtS;

public interface OTpdAgtSDao {

	
	List<OTpdAgtS> getAgt(OTpdAgtSPK build);

	OTpdAgtS getAgentType(OTpdAgtSPK pk);
	
}
