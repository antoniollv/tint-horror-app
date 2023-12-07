package com.mapfre.tron.api.cmn.thp.prs.dl;

import java.util.Map;

import com.mapfre.nwt.trn.thp.prs.bo.OThpPrsS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface IDlOThpPrsSDao extends NewtronDao<String, Map<String, Object>> {
	
	OThpPrsS getCtgNam(Map<String, Object> map);

}
