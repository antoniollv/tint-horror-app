
package com.mapfre.tron.api.prt.del.bl;

import java.util.List;

import com.mapfre.nwt.trn.nmr.gpp.bo.ONmrGppS;
import com.mapfre.nwt.trn.prt.del.bo.OPrtDelS;

/**
 * The thirdparty cmn business service interface.
 *
 * @author AMINJOU
 * @since 1.0.0
 * @version 01 mar. 2021 13:07:41
 *
 */
public interface IBlPrtDelQry {

    /**
     * 
     * Query group policies. 
     * It will return the group policies that belong  to a line of business. 
     * It will be mandatory send the parameters defined  in the service and 
     * the service will response with the output object defined.
     * 
     * @param lngVal   -> Language code
     * @param cmpVal   -> Company code
     * @param usrVal   -> User code
     * @param lobVal   -> Line of Business
     * @param vldDat   -> Valid Date
     * 
     * @return List<ONmrGppS>
     * 
     * 
     */
	List<OPrtDelS> get(String usrVal, String lngVal, Integer cmpVal, Long vldDat, Integer lobVal);


}
