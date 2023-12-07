package com.mapfre.tron.api.cmn.lsf.rek.dl;

import java.util.List;
import java.util.Map;

import com.mapfre.nwt.trn.lsf.rek.bo.OLsfRekS;

public interface IDlLsfRekDAO {

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
