/**
 * 
 */
package com.mapfre.tron.api.cmn.atr.bl;

import java.util.List;

import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;
import com.mapfre.tron.api.cmn.model.InVariableConceptsInformation;

/**
 * The IBLCmnAtrQry business logic service interface.
 *
 * @author majoguam
 * @since 1.8
 * @version 24/02/2021
 *
 */
public interface IBLCmnAtrQry {

	
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
