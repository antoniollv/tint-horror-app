package com.mapfre.tron.api.prt.sec.bl;

import java.util.List;

import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;
import com.mapfre.nwt.trn.prt.sec.bo.OPrtSecS;

/**
 *
 * @author aminjou
 * @since 1.0.0
 *
 */
public interface IBlPrtSec {


	/**
	 * Query sectors by company. 
	 * It will return the types of languages. 
	 * It will be mandatory send the parameters defined in the service and the service will response 
	 * with the output object defined. operationId: getsectorList
	 * 
	 * 
	 * @param cmpVal
	 * @param lngVal
	 * @param usrVal
	 * 
	 * @return List<OPrtSecS>
	 * 
	 */
	List<OPrtSecS> getSec(Integer cmpVal, String lngVal, String usrVal);

    /**
     * Query currency. It will return the currency
     *
     * @author Cristian Saball
     *
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> company
     * @param sdrCrnVal -> standard currency value
     * @return OCrnCrnS -> the currency
     */
    OPrtSecS getSecQry(Integer cmpVal, String lngVal, String usrVal, String secVal);

    
    /**
     * Query sector. It will return the sector
     *
     * @author Amin Joudi
     *
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
	 * @param secVal 	-> Sector
	 * @param sbsVal 	-> SubSector value
     * @return OPrtSecS -> the sector
     */
	OPrtSbsS getSubSector(Integer cmpVal, Integer secVal, Integer sbsVal, String lngVal, String usrVal);
}
