/**
 * 
 */
package com.mapfre.tron.api.cmn.rcd.bl;

import java.util.List;

import com.mapfre.nwt.trn.rcd.tmd.bo.ORcdTmdS;

/**
 * The CmnTypQry business logic service interface.
 *
 * @author pvraul1
 * @since 1.8
 * @version 16 sept. 2019 13:05:08
 *
 */
public interface IBLCmnRcdQry {


    /**
     * @author AMINJOU
     * 
     * Query collector types v1. 
     * It will return the types of collector. 
     * It will be mandatory send the parameters defined in the service and the service 
     * will response with the output object defined.
     * 
     * @param usrVal
     * @param lngVal
     * @param cmpVal
     * 
     * @return List<ORcdTmdS>
     * 
     */
	List<ORcdTmdS> getCollectorTypesV1(String usrVal, String lngVal, Integer cmpVal);

	
	
	   /**
     * @author AMINJOU
     * 
     * Query collector type v1. 
     * It will return the type of collector. 
     * It will be mandatory send the parameters defined in the service and the 
     * service will response with the output object defined.
     * 
     * @param usrVal
     * @param lngVal
     * @param cmpVal
     * @param mnrTypVal
     * 
     * @return ORcdTmdS
     * 
     */
	ORcdTmdS getCollectorTypeV1(String usrVal, String lngVal, Integer cmpVal, String mnrTypVal);
    

}
