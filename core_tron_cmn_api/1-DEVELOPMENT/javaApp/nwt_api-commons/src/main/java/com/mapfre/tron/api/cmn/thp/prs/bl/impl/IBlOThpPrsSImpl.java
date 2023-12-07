package com.mapfre.tron.api.cmn.thp.prs.bl.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.trn.thp.prs.bo.OThpPrsP;
import com.mapfre.nwt.trn.thp.prs.bo.OThpPrsS;
import com.mapfre.nwt.trn.thp.prs.sr.ISrThpPrsQry;
import com.mapfre.tron.api.cmn.ResetPacakge;
import com.mapfre.tron.api.cmn.thp.prs.bl.IBlOThpPrsS;
import com.mapfre.tron.api.cmn.thp.prs.dl.IDlOThpPrsSDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IBlOThpPrsSImpl implements IBlOThpPrsS {

	@Autowired
	protected ISrThpPrsQry iSrThpPrsQry;

	@Autowired
	protected IDlOThpPrsSDao iDlOThpPrsSDao;

	/** The resetPackage property. */
	@Autowired
	protected ResetPacakge resetPackage;

	/** The newtron utils. */
	protected NwtUtils nwtUtils;

	/**
	 * Get the utils property.
	 * 
	 * @return the utils property
	 */
	protected NwtUtils getNwtUtils() {
		if (nwtUtils == null) {
			nwtUtils = new NwtUtils();
		}
		return nwtUtils;
	}

	/** Reset the session. */
	protected void resetSession() {
		log.debug("Reseting session...");
		resetPackage.executeRP();
		log.debug("The session has been reset.");
	}

	@Override
	public OThpPrsP get(String thpDcmTypVal, String thpDcmVal, BigDecimal thpAcvVal, String usrVal, String lngVal,
			BigDecimal cmpVal) {

		log.info("acs - Query Person by document");

		// reseting session
		resetSession();

		OThpPrsP oThpPrsP = iSrThpPrsQry.get(cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal, lngVal);

		// comprobamos terminacion y en caso de contener errores se lanza excepcion
		getNwtUtils().isTrmOk(oThpPrsP, "iSrThpPrsQry.acs");

		return oThpPrsP;
	}

	@Override
	public OThpPrsS getCtgNam(Map<String, Object> map) {

		log.info("Get Gtg Nam");

		// reseting session
		resetSession();

		return iDlOThpPrsSDao.getCtgNam(map);

	}

}
