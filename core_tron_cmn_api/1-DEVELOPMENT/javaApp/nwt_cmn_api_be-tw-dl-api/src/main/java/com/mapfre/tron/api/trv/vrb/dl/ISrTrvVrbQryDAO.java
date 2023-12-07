package com.mapfre.tron.api.trv.vrb.dl;

import java.util.List;

import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;

/**
 * @author AMINJOU
 * @version 24/02/2021
 *
 */
public interface ISrTrvVrbQryDAO {

	
    /**
     * Query variable  definition by company. 
     * It will return the variable definition filtering by company
     * 
     * @param usrVal 		-> User code
     * @param lngVal 		-> Language code
     * @param cmpVal 		-> Company code
     * @param vrbNamVal 	-> Variable Name
     * @param vldDat 		-> Validation Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
	List<OTrnVrbS> get(String usrVal, String lngVal, Integer cmpVal, String vrbNamVal, Long vldDat);

	
	
	/**
	 * Query variable  definition by line of business. 
	 * It will return the variable definition filtering by company and line of business
	 * 
	 *  
     * @param usrVal 		-> User code
     * @param lngVal 		-> Language code
     * @param cmpVal 		-> Company code
     * @param vrbNamVal 	-> Variable Name
     * @param vldDat 		-> Validation Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
	List<OTrnVrbS> getVrb(String usrVal, String lngVal, Integer cmpVal, String vrbNamVal, Long vldDat, Integer lobVal);



	
	/**
	 * Variable Definition
	 * 
	 *  
     * @param usrVal 		-> User code
     * @param lngVal 		-> Language code
     * @param cmpVal 		-> Company code
     * @param thpDcmTypVal 	-> Document type
     * @param thpDcmVal		-> Document
     * @param thpAcvVal		-> Activity
     * @param vrbNamVal		-> Variable Name
     * @param vldDat		-> Valid Date
     *  
     * @return List<OTrnVrbS>
     * 
     */
	List<OTrnVrbS> getVrb(String usrVal, String lngVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal,
			Integer thpAcvVal, String vrbNamVal, Long vldDat);
               
	/**
	     * Update variable  definition by thrid party. It will update the variable definition  by third party
	     * 
	     * @param usrVal 		-> User code
	     * @param lngVal 		-> Language code
	     * @param cmpVal 		-> Company code
	     * @param thpDcmTypVal 	-> Document type
	     * @param thpDcmVal 	-> document
	     * @param thpAcvVal 	-> Activity
	     * @param vrbNamVal 	-> Variable Name
	     * @param inVariableDefinition 	-> Input data to update the variable definition
	     * 
	     * @return void
	     * 
	     */
	void upd(String usrVal, String lngVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal, 
		Integer thpAcvVal, String vrbNamVal, OTrnVrbS inVariableDefinition);


    /**
     * Query variable definition by company. It will return the variable definition
     * filtering by company
     * 
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param vrbNamVal -> Variable Name
     * @param vldDat    -> Validation Date
     * @param vrbVal -> Variable Name

     * @return OTrnVrbS
     * 
     */
    OTrnVrbS getById(String usrVal, String lngVal, Integer cmpVal, String vrbNamVal, Long vldDat, String vrbVal);

}
