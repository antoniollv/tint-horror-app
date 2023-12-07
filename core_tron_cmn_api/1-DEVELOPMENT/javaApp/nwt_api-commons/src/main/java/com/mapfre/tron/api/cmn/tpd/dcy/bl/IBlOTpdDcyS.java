package com.mapfre.tron.api.cmn.tpd.dcy.bl;

import java.util.Map;

import com.mapfre.nwt.trn.tpd.dcy.bo.OTpdDcyS;
import com.mapfre.nwt.trn.tpd.ssd.bo.OTpdSsdS;

public interface IBlOTpdDcyS {

	OTpdDcyS getOTpdDcyS(Map<String, Object> map);

	OTpdSsdS getOTpdSsdS(Map<String, Object> map);

}
