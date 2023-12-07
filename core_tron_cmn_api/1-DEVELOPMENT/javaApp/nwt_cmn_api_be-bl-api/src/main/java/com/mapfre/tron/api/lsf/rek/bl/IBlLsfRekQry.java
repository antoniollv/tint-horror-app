package com.mapfre.tron.api.lsf.rek.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.lsf.rek.bo.OLsfRekS;

/**
 * The IBlLsfRekQry business logic service interface.
 *
 * @author SCIKER9
 * @version 23/03/2023
 *
 */
public interface IBlLsfRekQry {

	/**
	 * Query Reason event locking definition
	 * 
	 * @param cmpVal -> Company code
	 * @param lngVal -> Language code
	 * @param usrVal -> User code
	 * 
	 * @return List<OLsfRekS>
	 */
	List<OLsfRekS> getReasonEventlockingDefinition(BigDecimal cmpVal, String lngVal, String usrVal);

}
