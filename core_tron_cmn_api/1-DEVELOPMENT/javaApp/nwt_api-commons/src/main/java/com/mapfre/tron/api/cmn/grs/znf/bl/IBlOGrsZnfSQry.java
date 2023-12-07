package com.mapfre.tron.api.cmn.grs.znf.bl;

import java.util.List;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.tron.api.cmn.grs.znf.dl.OgrsZnfPK;

public interface IBlOGrsZnfSQry {

    public OGrsZnfS getOGrsZnfS(OgrsZnfPK pk);

    public List<OGrsZnfS> getAllOGrsZnfS(OgrsZnfPK build);
}
