package com.mapfre.tron.api.cmn.lss.svo.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.lss.svo.bo.OLssSvoS;

public interface IDlLssSvoDAO {
	
	List<OLssSvoS> getServiceOrderQuery(BigDecimal cmpVal, String svoVal);

}
