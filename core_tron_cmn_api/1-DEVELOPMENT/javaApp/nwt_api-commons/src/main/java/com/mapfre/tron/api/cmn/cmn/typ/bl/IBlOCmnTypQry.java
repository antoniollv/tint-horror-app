package com.mapfre.tron.api.cmn.cmn.typ.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnCltPK;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnTypPK;

public interface IBlOCmnTypQry {

    public OCmnTypS getOCmnTypS(OCmnTypPK pk);
    
    public List<OCmnTypS> getListOCmnTypS(OCmnTypPK pk);
    
    public List<OCmnTypS> getAllTypes(OCmnTypPK pk);
    
    public OCmnTypS getCollectorType(OCmnCltPK pk);

}
