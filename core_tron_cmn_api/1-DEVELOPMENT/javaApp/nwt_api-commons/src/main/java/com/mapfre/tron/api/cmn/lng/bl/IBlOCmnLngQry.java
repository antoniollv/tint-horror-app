package com.mapfre.tron.api.cmn.lng.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngP;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.tron.api.cmn.lng.dl.OCmnLngQryPK;

public interface IBlOCmnLngQry {

    List<OCmnLngP> getOCmnLngPAll(String lngVal, BigDecimal cmpVal);

    OCmnLngS getLanguage(OCmnLngQryPK oCmnLngQryPK);
}
