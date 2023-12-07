package com.mapfre.tron.api.ard.aby.bl;

import java.util.List;

import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvS;
import com.mapfre.tron.api.ard.aby.dl.OArdAbvQryPK;

public interface IBlOArdAbvQry {

    List<OArdAbvS> tbl(OArdAbvQryPK pmOArdAbvQryPK);

}
