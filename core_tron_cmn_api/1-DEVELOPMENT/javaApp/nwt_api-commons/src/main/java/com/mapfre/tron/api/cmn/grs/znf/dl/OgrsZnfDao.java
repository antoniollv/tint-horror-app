package com.mapfre.tron.api.cmn.grs.znf.dl;

import java.util.List;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OgrsZnfDao extends NewtronDao<OGrsZnfS, OgrsZnfPK> {

    List<OGrsZnfS> getAllWithPK(OgrsZnfPK pk);

}
