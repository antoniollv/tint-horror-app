package com.mapfre.tron.api.cmn.lss.lsi.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.lss.lsi.bo.OLssLsiS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

public interface OLssLsiDao extends NewtronDao<OLssLsiS, OLssLsiPK>{
	
	 public List<OLssLsiS> getLossQuery(BigDecimal numSini, BigDecimal codCia);
    
}
