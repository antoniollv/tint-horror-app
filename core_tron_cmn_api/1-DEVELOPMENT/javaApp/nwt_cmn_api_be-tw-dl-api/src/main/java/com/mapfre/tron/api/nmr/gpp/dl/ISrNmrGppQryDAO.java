
package com.mapfre.tron.api.nmr.gpp.dl;

import java.util.List;

import com.mapfre.nwt.trn.nmr.gpp.bo.ONmrGppS;

/**
 * The ISrNmrGppQryDAO dao repository.
 *
 * @author AMINJOU
 *
 */
public interface ISrNmrGppQryDAO {

	
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
	List<ONmrGppS> get(String usrVal, String lngVal, Integer cmpVal, Long vldDat, Integer lobVal);

}
