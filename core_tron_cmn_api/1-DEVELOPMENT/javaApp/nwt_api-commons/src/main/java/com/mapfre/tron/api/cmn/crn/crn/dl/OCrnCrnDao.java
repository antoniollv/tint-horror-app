package com.mapfre.tron.api.cmn.crn.crn.dl;

import java.util.List;

import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OCrnCrnDao extends NewtronDao<OCrnCrnS, OCrnCrnPK>{

    List<OCrnCrnS> getAllWithCmpVal(OCrnCrnPK pk);

    OCrnCrnS getOCrnCrnSCurrency(OCrnCrnPK build);

}
