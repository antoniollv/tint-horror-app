package com.mapfre.tron.api.cmn.prt.sbs.bl;

import java.util.List;

import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;
import com.mapfre.tron.api.cmn.prt.sbs.dl.OPrtSbsPK;
import com.mapfre.tron.api.cmn.prt.sbs.dl.OPrtSbsPK2;

public interface IBlOPrtSbsQry {

    List<OPrtSbsS> getSubSectorList(OPrtSbsPK build);

	OPrtSbsS getSubSector(OPrtSbsPK2 build);

}
