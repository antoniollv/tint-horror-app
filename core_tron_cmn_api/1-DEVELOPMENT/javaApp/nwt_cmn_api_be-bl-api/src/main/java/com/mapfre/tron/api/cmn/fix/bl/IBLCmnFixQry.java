package com.mapfre.tron.api.cmn.fix.bl;

import java.util.List;

import com.mapfre.nwt.trn.ard.fxa.bo.OArdFxaS;

public interface IBLCmnFixQry {

    List<OArdFxaS> getFixedAttributes(Integer cmpVal, String lngVal, String usrVal);

}
