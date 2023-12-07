package com.mapfre.tron.api.cmn.thp.cst.dl;

import java.util.List;

import com.mapfre.nwt.trn.tpd.csg.bo.OTpdCsgS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OTpdCsgSDao extends NewtronDao<OTpdCsgS, OTpdCsgSPK> {

    OTpdCsgS getOTpdCsgS(OTpdCsgSPK pk);

    List<OTpdCsgS> getOTpdCsgSList(OTpdCsgSPK pk);

}
