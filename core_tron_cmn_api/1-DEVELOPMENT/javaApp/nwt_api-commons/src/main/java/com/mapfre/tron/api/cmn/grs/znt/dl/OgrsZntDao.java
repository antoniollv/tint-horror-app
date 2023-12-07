package com.mapfre.tron.api.cmn.grs.znt.dl;

import java.util.List;

import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OgrsZntDao extends NewtronDao<OGrsZntS, OgrsZntPK> {

    List<OGrsZntS> getAllWithPK(OgrsZntPK pk);
}
