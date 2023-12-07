package com.mapfre.tron.api.cmn.lss.fli.dl;

import com.mapfre.nwt.trn.lss.fli.bo.OLssFliS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OLssFliDao extends NewtronDao<OLssFliS, OLssFliPK>{
    
    public OLssFliS getOLssFliSTypNam(OLssFliPK pk);

}
