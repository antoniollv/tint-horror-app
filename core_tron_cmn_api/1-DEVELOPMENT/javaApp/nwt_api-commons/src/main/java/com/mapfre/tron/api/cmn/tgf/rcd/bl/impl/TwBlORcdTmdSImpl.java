package com.mapfre.tron.api.cmn.tgf.rcd.bl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.rcd.tmd.bo.ORcdTmdS;
import com.mapfre.tron.api.cmn.rcd.ucd.bl.IBlORcdUcdS;
import com.mapfre.tron.api.cmn.rcd.ucd.dl.ORcdTmdSDao;
import com.mapfre.tron.api.cmn.rcd.ucd.dl.ORcdTmdSPK;

@Service
public class TwBlORcdTmdSImpl implements IBlORcdUcdS {

	@Autowired
	protected ORcdTmdSDao oRcdTmdSDao;
	

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
	@Override
	public ORcdTmdS getCollectorTypeV1(ORcdTmdSPK pk) {
		return oRcdTmdSDao.getCollectorTypeV1(pk);
	}

	
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
	@Override
	public List<ORcdTmdS> getCollectorTypesV1(ORcdTmdSPK pk) {
		return oRcdTmdSDao.getCollectorTypesV1(pk);
	}

}
