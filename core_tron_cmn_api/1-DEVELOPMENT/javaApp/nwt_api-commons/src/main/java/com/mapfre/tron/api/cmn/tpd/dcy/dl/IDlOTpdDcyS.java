package com.mapfre.tron.api.cmn.tpd.dcy.dl;

import java.util.Map;

import com.mapfre.nwt.trn.tpd.dcy.bo.OTpdDcyS;
import com.mapfre.nwt.trn.tpd.ssd.bo.OTpdSsdS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface IDlOTpdDcyS  extends NewtronDao<String, Map<String, Object>>{

	OTpdDcyS getOTpdDcyS(Map<String, Object> map);

	OTpdSsdS getOTpdSsdS(Map<String, Object> map);

}
