package com.mapfre.tron.api.cmn.thp.cst.bl;

import java.util.List;

import com.mapfre.nwt.trn.tpd.csg.bo.OTpdCsgS;
import com.mapfre.tron.api.cmn.thp.cst.dl.OTpdCsgSPK;

public interface IBlOTpdCsgS {

    OTpdCsgS getOTpdCsgS(OTpdCsgSPK pk);

    List<OTpdCsgS> getOTpdCsgSList(OTpdCsgSPK pk);

}
