package com.mapfre.tron.api.cmn.tgf.ucd.dl;

import java.util.List;

import com.mapfre.nwt.trn.tgf.ucd.bo.OTgfUcdS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OTgfUcdSDao  extends NewtronDao<OTgfUcdS, OTgfUcdSPK> {


	List<OTgfUcdS> getCauses(OTgfUcdSPK pk);
	
	OTgfUcdS getCause(OTgfUcdSPK2 pk);

}
