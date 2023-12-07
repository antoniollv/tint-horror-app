package com.mapfre.tron.api.cmn.ssg.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.ssg.bo.OCmnSsgS;

/**
 * The CmnSsgQry interface.
 *
 * @author Cristian Saball
 * @version 01 feb. 2022
 *
 */
public interface IBlCmnSsgQry {

    /**
     * Query Storage From Self-Service.
     *
     * @author Cristian Saball
     *
     * @param cmpVal 	-> Company code
     * @param usrVal 	-> User code
     * @param lngVal 	-> Language code
     * @param sfrSciVal -> Self-Service Section
     * @param sfrSbsVal -> Self-Service Subsection
     * @param sfrIdnVal -> Authentication Identifier
     * @param vrbDspVal -> Variable Description
     * @return       	-> The variables saved for the self service front end
     */
    List<OCmnSsgS> getStorageFromSelfService(Integer cmpVal, String usrVal, String lngVal, String sfrSciVal,
	    String sfrSbsVal, List<String> sfrIdnVal, String vrbDspVal);

}
