package com.mapfre.tron.api.cmn.lss.lsi.bl.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.lss.lsi.bo.OLssLsiS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.lss.lsi.bl.IBlOLssLsiSQry;
import com.mapfre.tron.api.cmn.lss.lsi.dl.OLssLsiDao;
import com.mapfre.tron.api.cmn.lss.lsi.dl.OLssLsiPK;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TwBlOLssLsiSQry extends TwBlCmnBase implements IBlOLssLsiSQry {

	@Autowired
	protected OLssLsiDao oLssLsiDao;

	@Override
	public OLssLsiS getOLssLsiSRsnNam(OLssLsiPK pk) {

		return oLssLsiDao.get(pk);

	}



	@Override
	public OLssLsiS getLossQuery(BigDecimal numSini, BigDecimal codCia) {

		log.info("Tronweb business logic implementation getAttributesFromServce have been called...");


		List<OLssLsiS> oLssLsiS = oLssLsiDao.getLossQuery(numSini,codCia);

		if (oLssLsiS == null) {

			throw new EmptyResultDataAccessException(ERROR_CODE);
		}

		return oLssLsiS.get(0);

	}

}
