package com.mapfre.tron.api.cmn.ard.fxa.bl;

import java.util.List;

import com.mapfre.nwt.trn.ard.fxa.bo.OArdFxaS;
import com.mapfre.tron.api.cmn.ard.fxa.dl.OArdFxaPK;

public interface IBlArdFxaQry {

    public List<OArdFxaS> getListOArdFxaS(OArdFxaPK pk);
    
}
