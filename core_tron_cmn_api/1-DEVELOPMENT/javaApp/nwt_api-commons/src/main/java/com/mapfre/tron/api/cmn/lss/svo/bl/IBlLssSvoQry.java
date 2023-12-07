package com.mapfre.tron.api.cmn.lss.svo.bl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.lss.svo.bo.OLssSvoS;

@Service
public interface IBlLssSvoQry {
	
	OLssSvoS getServiceOrderQuery(BigDecimal cmpVal, String svoVal);

}
