package com.mapfre.tron.api.cmn.prt.sec.bl;

import java.util.List;

import com.mapfre.nwt.trn.prt.sec.bo.OPrtSecS;
import com.mapfre.tron.api.cmn.prt.sec.dl.OPrtSecPK;

public interface IBlOPrtSecQry {
    
    public OPrtSecS getSecNam(OPrtSecPK pk);

	public List<OPrtSecS> getSecList(OPrtSecPK pk);

}
