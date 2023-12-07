package com.mapfre.tron.api.cmn.mvr.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;
import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;

/**
 * The CmnMvrCrt business logic service interface.
 *
 * @author ibermatica - Borja Ruiz
 * @since 1.8
 * @version 16 sept. 2020 13:05:08
 *
 */
public interface IBlCmnMvrCrt {

	/**
	 * Create movement record for a third party
	 * 
	 * @author Cristian Saball
	 * 
	 * @param lngVal           -> Language code
	 * @param usrVal           -> User code
	 * @param cmpVal           -> Company code
	 * @param thpDcmTypVal     ->Document type
	 * @param thpDcmVal        -> Document
	 * @param thpAcvVal        -> Activity
	 * @param thpAcvVal        -> Activity
	 * @param inMovementRecord -> Input data to new movement record
	 * @return -> void
	 */
	void mvrOch(String lngVal, String usrVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal, Integer thpAcvVal,
			OCmnMvrS inMovementRecord);

	OCmnMvrS postMovementRecordbyNote(BigDecimal cmpVal, String nteVal, String mvmldn, String lngVal,
			String usrVal, BigDecimal lssVal, String plyVal, Integer rskVal, List<OTrnVrbS> oTrnVrbS);

}
