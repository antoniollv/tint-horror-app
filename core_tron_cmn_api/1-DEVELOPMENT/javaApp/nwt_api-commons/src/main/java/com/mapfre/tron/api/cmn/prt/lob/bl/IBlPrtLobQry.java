
package com.mapfre.tron.api.cmn.prt.lob.bl;

import java.util.List;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.prt.lob.dl.OPrtLobQryPK;
import com.mapfre.tron.api.cmn.prt.lob.dl.OPrtLobVdaPK;

public interface IBlPrtLobQry {

    OPrtLobS lob(OPrtLobQryPK pmOPrtLobQryPK);

    List<OPrtLobS> get(OPrtLobVdaPK pmOPrtLobVdaPK);
}
