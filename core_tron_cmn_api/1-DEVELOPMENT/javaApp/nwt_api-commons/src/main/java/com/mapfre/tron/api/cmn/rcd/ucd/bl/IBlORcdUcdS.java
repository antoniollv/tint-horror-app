package com.mapfre.tron.api.cmn.rcd.ucd.bl;

import java.util.List;

import com.mapfre.nwt.trn.rcd.tmd.bo.ORcdTmdS;
import com.mapfre.tron.api.cmn.rcd.ucd.dl.ORcdTmdSPK;

public interface IBlORcdUcdS {
	
	
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
	ORcdTmdS getCollectorTypeV1(ORcdTmdSPK pk);

	
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
	List<ORcdTmdS> getCollectorTypesV1(ORcdTmdSPK pk);

}
