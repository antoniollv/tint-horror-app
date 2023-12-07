package com.mapfre.tron.api.cmn.prt.lob.cache.dl;

import java.util.List;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OPrtLobCacheDao extends NewtronDao<OPrtLobS, OPrtLobPK> {

    List<OPrtLobS> getAllForLineOfBusiness(OPrtLobPK pk);
}
