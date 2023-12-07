package com.mapfre.tron.api.cmn.cmu.cmp.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.tron.api.cmn.cmu.cmp.dl.OCmuCmpPK;

public interface IBlOCmuCmpQry {

    public OCmuCmpS getCmpNam(OCmuCmpPK pk);

    public OCmuCmpS getCmpNam(String usrVal, String lngVal, OCmuCmpPK pk);

    public List<OCmuCmpS> getCmpNamList(String usrVal, String lngVal);

}
