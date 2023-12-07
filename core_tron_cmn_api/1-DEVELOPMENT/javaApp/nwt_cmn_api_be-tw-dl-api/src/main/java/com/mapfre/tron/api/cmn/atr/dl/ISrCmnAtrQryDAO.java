package com.mapfre.tron.api.cmn.atr.dl;

import java.util.List;

import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;
import com.mapfre.tron.api.cmn.model.InVariableConceptsInformation;

/**
 * @author AMINJOU
 * @version 24/02/2021
 *
 */
public interface ISrCmnAtrQryDAO {

	/**
	 * 
	 * @param usrVal -> user value
	 * @param lngVal -> language value
	 * @param inConstantInformation 
	 * @return List<OPlyAtrS>
	 * 
	 */
	List<OPlyAtrS> atr(String usrVal, String lngVal, InVariableConceptsInformation inConstantInformation);

}
