package com.mapfre.tron.api.cmn.grs.zof.bl;

import java.util.List;

import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;
import com.mapfre.tron.api.cmn.grs.zof.dl.OgrsZofPK;

public interface IBlOGrsZofSQry {
    
    public List<OGrsZofS> getZoneFiveByPslCodVal(OgrsZofPK pk);
    
    public List<OGrsZofS> getZoneFiveList(OgrsZofPK pk);

}
