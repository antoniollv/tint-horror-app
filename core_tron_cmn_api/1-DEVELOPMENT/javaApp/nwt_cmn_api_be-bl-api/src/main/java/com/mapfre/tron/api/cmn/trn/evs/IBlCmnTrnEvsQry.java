package com.mapfre.tron.api.cmn.trn.evs;

import com.mapfre.nwt.trn.trn.evn.bo.OTrnEvnS;

/**
 * @author AMINJOU
 *
 */
public interface IBlCmnTrnEvsQry {

	/**
	 * update received event as processed
	 * 
	 * @param cmpVal
	 * @param lngVal
	 * @param usrVal
	 * @param oTrnEvnS
	 * 
	 */
	void setUpdateEvent(Integer cmpVal, String lngVal, String usrVal, OTrnEvnS oTrnEvnS);
	
}
