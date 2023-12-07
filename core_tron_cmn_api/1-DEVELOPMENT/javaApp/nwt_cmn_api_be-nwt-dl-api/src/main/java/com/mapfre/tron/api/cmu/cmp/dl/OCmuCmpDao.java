package com.mapfre.tron.api.cmu.cmp.dl;



import java.util.List;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OCmuCmpDao extends NewtronDao<OCmuCmpS, OCmuCmpPK> {

    List<OCmuCmpS> getAll(String usrVal);

}
