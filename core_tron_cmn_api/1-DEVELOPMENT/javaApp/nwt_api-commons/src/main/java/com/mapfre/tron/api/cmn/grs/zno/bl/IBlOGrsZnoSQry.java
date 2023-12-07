package com.mapfre.tron.api.cmn.grs.zno.bl;

import java.util.List;

import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;
import com.mapfre.tron.api.cmn.grs.zno.dl.OgrsZnoPK;

public interface IBlOGrsZnoSQry {

    public OGrsZnoS getOGrsZnoS(OgrsZnoPK pk);

    public List<OGrsZnoS> getAllOGrsZnoS(OgrsZnoPK pk);
}
