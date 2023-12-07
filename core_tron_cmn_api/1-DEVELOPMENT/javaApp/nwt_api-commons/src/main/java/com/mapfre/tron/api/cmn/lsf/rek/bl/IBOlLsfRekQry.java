package com.mapfre.tron.api.cmn.lsf.rek.bl;

import java.util.List;
import java.util.Map;

import com.mapfre.nwt.trn.lsf.rek.bo.OLsfRekS;

/**
 * The IBlLsfRekQry business logic service interface.
 *
 * @author SCIKER9
 * @version 23/03/2023
 *
 */
public interface IBOlLsfRekQry {

	/**
	 * Query Reason event locking definition
	 * 
	 * @param cmpVal -> Company code
	 * @param lngVal -> Language code
	 * @param usrVal -> User code
	 * 
	 * @return List<OLsfRekS>
	 */
	List<OLsfRekS> getOLsfRekSList(Map<String, Object> map);

}
