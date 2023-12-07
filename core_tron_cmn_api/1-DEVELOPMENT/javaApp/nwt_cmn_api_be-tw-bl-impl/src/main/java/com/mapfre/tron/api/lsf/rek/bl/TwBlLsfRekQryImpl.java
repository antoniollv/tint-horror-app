package com.mapfre.tron.api.lsf.rek.bl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.lsf.rek.bo.OLsfRekS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The IBlLsfRekQry business logic service implementation.
 *
 * @author SCIKER9
 * @version 23/03/2023
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlLsfRekQryImpl extends TwBlCmnBase implements IBlLsfRekQry {

	/**
	 * Query Reason event locking definition
	 * 
	 * @param cmpVal -> Company code
	 * @param lngVal -> Language code
	 * @param usrVal -> User code
	 * 
	 * @return List<OLsfRekS>
	 */
	@Override
	public List<OLsfRekS> getReasonEventlockingDefinition(BigDecimal cmpVal, String lngVal, String usrVal) {
		log.debug("Tronweb business logic implementation getReasonEventlockingDefinition have been called...");

		resetSession();

		List<OLsfRekS> oLsfRekSLst = null;
		try {
			oLsfRekSLst = cacheableAttribute.getReasonEventlockingDefinition(cmpVal, lngVal, usrVal);

			if (oLsfRekSLst == null || oLsfRekSLst.isEmpty()) {
				throw new EmptyResultDataAccessException(ERROR_CODE);
			}

			return oLsfRekSLst;

		} catch (IncorrectResultSizeDataAccessException e) {
			getErrorWithoutPrpIdn(lngVal, "REK", cmpVal);
		}

		return oLsfRekSLst;
	}
}