package com.mapfre.tron.api.cmn.grs.zot.dl;

import java.util.List;

import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OgrsZotDao extends NewtronDao<OGrsZotS, OgrsZotPK> {

    List<OGrsZotS> getAllWithPK(OgrsZotPK pk);

}
