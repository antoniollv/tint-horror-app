package com.mapfre.tron.api.cmn.lss.lsi.bl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.lss.lsi.bo.OLssLsiS;
import com.mapfre.tron.api.cmn.lss.lsi.dl.OLssLsiPK;

public interface IBlOLssLsiSQry {

    public OLssLsiS getOLssLsiSRsnNam(OLssLsiPK pk);
    
    public OLssLsiS getLossQuery(BigDecimal numSini, BigDecimal codCia);
    

}
