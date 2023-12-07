package com.mapfre.tron.api.cmn.crn.crn.bl;

import java.util.List;

import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnP;
import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;
import com.mapfre.tron.api.cmn.crn.crn.dl.OCrnCrnPK;

public interface IBlOCrnCrnQry {

    OCrnCrnS getSdrCrnVal(OCrnCrnPK pmOCrnCrnPK);
    
    OCrnCrnS getOCrnCrnS(OCrnCrnPK pmOCrnCrnPK);

    List<OCrnCrnP> getOCrnCrnSAll(OCrnCrnPK pk);

    OCrnCrnS getOCrnCrnSCurrency(OCrnCrnPK build);
}
