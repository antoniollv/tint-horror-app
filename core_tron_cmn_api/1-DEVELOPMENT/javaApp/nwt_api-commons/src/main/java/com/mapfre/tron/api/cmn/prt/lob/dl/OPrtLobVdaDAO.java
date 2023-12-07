
package com.mapfre.tron.api.cmn.prt.lob.dl;

import java.util.List;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OPrtLobVdaDAO extends NewtronDao<OPrtLobS, OPrtLobVdaPK> {

    List<OPrtLobS> getAllWithPK(OPrtLobVdaPK pk);

}
