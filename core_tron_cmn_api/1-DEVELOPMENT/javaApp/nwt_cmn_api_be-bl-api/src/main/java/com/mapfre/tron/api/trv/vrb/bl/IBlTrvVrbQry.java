/**
 * 
 */
package com.mapfre.tron.api.trv.vrb.bl;

import java.util.List;

import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;

/**
 * The IBLCmnAtrQry business logic service interface.
 *
 * @author majoguam
 * @since 1.8
 * @version 24/02/2021
 *
 */
public interface IBlTrvVrbQry {

	
    /**
     * Query variable definition by company. It will return the variable definition
     * filtering by company
     * 
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param vrbNamVal -> Variable Name
     * @param vldDat    -> Validation Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
    List<OTrnVrbS> get(String usrVal, String lngVal, Integer cmpVal, String vrbNamVal, Long vldDat);

    /**
     * Query variable definition by company by id. It will return the variable definition
     * filtering by company and by id
     * 
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param vrbNamVal -> Variable Name
     * @param vrbVal -> Variable
     * @param vldDat    -> Validation Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
    OTrnVrbS getById(String usrVal, String lngVal, Integer cmpVal, String vrbNamVal, Long vldDat, String vrbVal);
	
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
	 * @param lobVal 
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
	List<OTrnVrbS> getThp(String usrVal, String lngVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal,
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
	void upd(String usrVal, String lngVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal, Integer thpAcvVal,
		String vrbNamVal, OTrnVrbS inVariableDefinition);




}
