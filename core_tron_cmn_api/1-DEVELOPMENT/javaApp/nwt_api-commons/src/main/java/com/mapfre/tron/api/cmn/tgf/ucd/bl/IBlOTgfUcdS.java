package com.mapfre.tron.api.cmn.tgf.ucd.bl;

import java.util.List;

import com.mapfre.nwt.trn.tgf.ucd.bo.OTgfUcdS;
import com.mapfre.tron.api.cmn.tgf.ucd.dl.OTgfUcdSPK;
import com.mapfre.tron.api.cmn.tgf.ucd.dl.OTgfUcdSPK2;

public interface IBlOTgfUcdS {
	
	List<OTgfUcdS> getCauses(OTgfUcdSPK pk);
	
	OTgfUcdS getCause(OTgfUcdSPK2 pk);

}
