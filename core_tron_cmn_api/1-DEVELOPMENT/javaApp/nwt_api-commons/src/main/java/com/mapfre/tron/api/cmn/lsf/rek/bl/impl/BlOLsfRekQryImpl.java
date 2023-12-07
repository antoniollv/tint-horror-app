package com.mapfre.tron.api.cmn.lsf.rek.bl.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.lsf.rek.bo.OLsfRekS;
import com.mapfre.tron.api.cmn.lsf.rek.bl.IBOlLsfRekQry;
import com.mapfre.tron.api.cmn.lsf.rek.dl.IDlLsfRekDAO;

/**
 * The IBOlLsfRekQry business logic service implementation.
 *
 * @author SCIKER9
 * @version 23/03/2023
 *
 */
@Service
public class BlOLsfRekQryImpl implements IBOlLsfRekQry {

	@Autowired
	protected IDlLsfRekDAO iDlLsfRekDAO;

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
	public List<OLsfRekS> getOLsfRekSList(Map<String, Object> map) {
		return iDlLsfRekDAO.getOLsfRekSList(map);
	}
}