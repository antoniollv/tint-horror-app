package com.mapfre.tron.api.cmn.grs.zot.bl;

import java.util.List;

import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;
import com.mapfre.tron.api.cmn.grs.zot.dl.OgrsZotPK;

public interface IBlOGrsZotSQry {

    public OGrsZotS getOGrsZotS(OgrsZotPK pk);

    List<OGrsZotS> getAllOGrsZotS(OgrsZotPK pk);
}
