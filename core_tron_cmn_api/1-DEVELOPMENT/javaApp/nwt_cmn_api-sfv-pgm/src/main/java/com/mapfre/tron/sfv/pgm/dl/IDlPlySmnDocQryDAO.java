package com.mapfre.tron.sfv.pgm.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.tron.sfv.bo.OPlySmnDocQryS;


public interface IDlPlySmnDocQryDAO {

	/**
	 * Get document name and documentum identifier from DF_CMN_NWT_XX_VRB_CNC
	 * 
	 * @param cmpVal -> Company value
	 * @param lobVal -> Line of business
	 * @param vrbNam -> Variable name
	 * @return String
	 * 
	 */
	List<OPlySmnDocQryS> getDocNam(BigDecimal cmpVal, BigDecimal lobVal, String vrbNam);

	/**
	 * Get document name and documentum identifier from DF_CMN_NWT_XX_VRB_CNC by like
	 * 
	 * @param cmpVal -> Company value
	 * @param lobVal -> Line of business
	 * @param vrbNam -> Variable name (like)
	 * @return String
	 * 
	 */
	List<OPlySmnDocQryS> getDocNamLike(BigDecimal cmpVal, BigDecimal lobVal, String vrbNam);
}
