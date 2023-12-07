package com.mapfre.tron.api.cmn.rcp.pmr.bl;

import java.util.List;

import com.mapfre.nwt.trn.rcd.tsd.bo.ORcdTsdS;
import com.mapfre.nwt.trn.rcp.pmr.bo.ORcpPmrS;
import com.mapfre.tron.api.cmn.rcp.pmr.dl.ORcdTsdSPK;
import com.mapfre.tron.api.cmn.rcp.pmr.dl.ORcpPmrPK;

public interface IBlORcpPmrSQry {

    public ORcpPmrS getORcpPmrSTypNam(ORcpPmrPK pk);

    public ORcpPmrS getORcpPmrSCrnNam(ORcpPmrPK build);
    
    public List<ORcdTsdS> getORcdTsdSList();

    public ORcdTsdS getORcdTsdS(ORcdTsdSPK oRcdTsdSPK);
}
