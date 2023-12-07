package com.mapfre.tron.api.cmn.grs.znt.bl;

import java.util.List;

import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;
import com.mapfre.tron.api.cmn.grs.znt.dl.OgrsZntPK;

public interface IBlOGrsZntSQry {

    public OGrsZntS getOGrsZntS(OgrsZntPK pk);
    
    List<OGrsZntS> getAllOGrsZntS(OgrsZntPK pk);
}
