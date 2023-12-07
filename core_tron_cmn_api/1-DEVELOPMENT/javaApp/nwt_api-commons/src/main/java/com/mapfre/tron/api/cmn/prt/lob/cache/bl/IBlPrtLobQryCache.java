package com.mapfre.tron.api.cmn.prt.lob.cache.bl;

import java.util.List;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.prt.lob.cache.dl.OPrtLobPK;

public interface IBlPrtLobQryCache {

    OPrtLobS getLobNam(OPrtLobPK pk);
    
    List<OPrtLobS> getAllForLineOfBusiness(OPrtLobPK pk);
}
