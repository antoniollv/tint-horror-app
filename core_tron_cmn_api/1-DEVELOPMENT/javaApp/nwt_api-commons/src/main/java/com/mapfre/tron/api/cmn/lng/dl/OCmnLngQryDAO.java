package com.mapfre.tron.api.cmn.lng.dl;

import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngP;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OCmnLngQryDAO extends NewtronDao<OCmnLngP, OCmnLngQryPK>{

    OCmnLngS getLanguage(OCmnLngQryPK pmOCmnLngQryPK);


}
