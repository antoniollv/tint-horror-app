package com.mapfre.tron.api.cmn.cmn.typ.dl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OCmnTypDao extends NewtronDao<OCmnTypS, OCmnTypPK> {
    
    List<OCmnTypS> getAllWithPK(OCmnTypPK pk);

    List<OCmnTypS> getAllTypes(OCmnTypPK pk);
    
    OCmnTypS getCollectorType(OCmnCltPK pk);


}
