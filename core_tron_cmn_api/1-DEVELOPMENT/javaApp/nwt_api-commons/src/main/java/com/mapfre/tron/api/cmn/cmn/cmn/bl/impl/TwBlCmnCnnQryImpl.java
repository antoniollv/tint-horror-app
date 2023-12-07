package com.mapfre.tron.api.cmn.cmn.cmn.bl.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.cnn.bo.OCmnCnnS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.cmn.cmn.bl.IBlCmnCnnQry;
import com.mapfre.tron.api.cmn.cmn.cmn.dl.IDlCmnCnnDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmnCnnQryImpl extends TwBlCmnBase implements IBlCmnCnnQry {

	@Autowired
	protected IDlCmnCnnDAO iDlCmnCnnDAO;

	/**
	 * Query Constant definition. It will return the information of a constant.
	 * 
	 * @param cmpVal      -> Company code
	 * @param vrbNam      -> Variable name
	 * @param usrVal      -> User code
	 * @param lngVal      -> Language code
	 * @param inDataQuery -> Input data to get constant value
	 * @param vldDat      -> Validation Date
	 * @return List<OCmnCnnS>
	 * 
	 */
	@Cacheable("PoC-postConstantDefinition")
	@Override
	public List<OCmnCnnS> postConstantDefinition(Integer cmpVal, String vrbNam, String usrVal, String lngVal,
			OCmnCnnS inDataQuery, Long vldDat) {

		log.debug("Tronweb business logic implementation postConstantDefinition have been called...");

		// reseting session
		resetSession();

		BigDecimal cmpValue = cmpVal != null ? new BigDecimal(cmpVal) : null;
		Date vldDate = vldDat != null ? new Date(vldDat) : null;

		List<OCmnCnnS> oCmnCnnSLst = null;
		try {
			oCmnCnnSLst = iDlCmnCnnDAO.postConstantDefinition(cmpValue, vrbNam, usrVal, lngVal, inDataQuery, vldDate);

			if (oCmnCnnSLst == null || oCmnCnnSLst.isEmpty()) {
				throw new EmptyResultDataAccessException(ERROR_CODE);
			}

			return oCmnCnnSLst;
		} catch (IncorrectResultSizeDataAccessException e) {
			getErrorWithoutPrpIdn(lngVal, "CNN", cmpValue);
		}

		return oCmnCnnSLst;
	}

}
