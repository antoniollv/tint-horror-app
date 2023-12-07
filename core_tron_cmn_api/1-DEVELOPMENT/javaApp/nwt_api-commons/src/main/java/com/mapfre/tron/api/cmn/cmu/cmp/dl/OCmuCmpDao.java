package com.mapfre.tron.api.cmn.cmu.cmp.dl;

import java.util.List;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OCmuCmpDao extends NewtronDao<OCmuCmpS, OCmuCmpPK> {

    public List<OCmuCmpS> getAll2();

    public OCmuCmpS get2(OCmuCmpPK pk);

}
