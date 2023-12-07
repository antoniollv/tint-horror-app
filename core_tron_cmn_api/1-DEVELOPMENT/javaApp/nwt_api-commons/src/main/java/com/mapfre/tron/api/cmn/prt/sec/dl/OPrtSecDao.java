package com.mapfre.tron.api.cmn.prt.sec.dl;

import java.util.List;

import com.mapfre.nwt.trn.prt.sec.bo.OPrtSecS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OPrtSecDao extends NewtronDao<OPrtSecS, OPrtSecPK>{

	public List<OPrtSecS> getSecList(OPrtSecPK pk);

}
