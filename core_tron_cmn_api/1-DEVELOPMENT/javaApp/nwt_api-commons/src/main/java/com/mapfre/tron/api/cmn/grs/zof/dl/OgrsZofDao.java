package com.mapfre.tron.api.cmn.grs.zof.dl;

import java.util.List;

import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OgrsZofDao extends NewtronDao<OGrsZofS, OgrsZofPK> {

    List<OGrsZofS> getZoneFiveByPslCodVal(OgrsZofPK pk);

    List<OGrsZofS> getZoneFiveList(OgrsZofPK pk);

}
