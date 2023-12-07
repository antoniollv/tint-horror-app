package com.mapfre.tron.api.cmn.lss.svo.bl.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.lss.svo.bo.OLssSvoS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.lss.svo.bl.IBlLssSvoQry;
import com.mapfre.tron.api.cmn.lss.svo.dl.IDlLssSvoDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlCmnLssSvoQryImpl extends TwBlCmnBase implements IBlLssSvoQry {

	/** The repository. */
	@Autowired
	protected IDlLssSvoDAO iDlLssSvoDAO;

	@Override
	public OLssSvoS getServiceOrderQuery(BigDecimal cmpVal, String svoVal) {

		log.info("Tronweb business logic implementation getAttributesFromServce have been called...");

		List<OLssSvoS> oLssSvoS = iDlLssSvoDAO.getServiceOrderQuery(cmpVal, svoVal);

		if (oLssSvoS == null || oLssSvoS.isEmpty()) {
			throw new EmptyResultDataAccessException(ERROR_CODE);
		}

		return oLssSvoS.get(0);

	}

}
