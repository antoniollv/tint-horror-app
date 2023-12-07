package com.mapfre.tron.api.cmn.cmn.cmn.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.cnn.bo.OCmnCnnS;

public interface IBlCmnCnnQry {

	/**
	 * Query Constant definition. It will return the information of a constant.
	 * 
	 * @param cmpVal      -> Company code
	 * @param vrbNam      -> Variable name
	 * @param usrVal      -> User code
	 * @param lngVal      -> Language code
	 * @param inDataQuery -> Input data to get constant value
	 * @param vldDat      -> Validation Date
	 * @return List<OCmnCnnS>
	 * 
	 */
	List<OCmnCnnS> postConstantDefinition(Integer cmpVal, String vrbNam, String usrVal, String lngVal,
			OCmnCnnS inDataQuery, Long vldDat);

}
