package com.mapfre.tron.api.cmn.prt.sbs.dl;

import java.util.List;

import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OPrtSbsDao extends NewtronDao<OPrtSbsS, OPrtSbsPK>{

    List<OPrtSbsS> getSubSectorList(OPrtSbsPK build);
    
    OPrtSbsS getSubSector(OPrtSbsPK2 build);

}
