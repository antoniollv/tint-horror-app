package com.mapfre.tron.api.cmn.psf.psf.bl;

import com.mapfre.nwt.trn.psf.psf.bo.OPsfPsfS;
import com.mapfre.tron.api.cmn.psf.psf.dl.OPsfPsfPK;

public interface IBlOPsfPsfQry {
    
    public OPsfPsfS getPmsNam(OPsfPsfPK pk, String lngVal);


}
