package com.mapfre.tron.api.cmn.trn.evs;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.evn.bo.OTrnEvnS;

/**
 * @author AMINJOU
 *
 */
public interface IDlTrnEvsDAO {

	/**
	 * update received event as processed
	 * 
	 * @param cmpVal
	 * @param lngVal
	 * @param usrVal
	 * @param oTrnEvnS
	 * @return 
	 * 
	 */
	int setUpdateEvent(BigDecimal cmpValValue, String lngVal, String usrVal, OTrnEvnS oTrnEvnS);

}
