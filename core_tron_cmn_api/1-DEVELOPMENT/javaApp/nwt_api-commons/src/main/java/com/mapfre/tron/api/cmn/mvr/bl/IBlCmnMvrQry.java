package com.mapfre.tron.api.cmn.mvr.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;

/**
 * The CmnMvrQry business logic service interface.
 *
 * @author Javier Sangil
 *
 */
public interface IBlCmnMvrQry {
    
    /**
     * Query movements record by third party
     * 
     * @author Javier Sangil
     *  
     * @param lngVal -> Language code
     * @param usrVal -> User code
     * @param cmpVal --> Company code 
     * @param thpDcmTypVal -> Document type
     * @param thpDcmVal -> document
     * @param thpAcvVal -> Activity 
     * @param mvmIdn -> Third Channel Distribution
     * @param qryDat -> Initial Date
     * @param mvmSttTypVal -> Movement state type
     * @param mvmPbl -> Public Movement
     * @param stsTypVal -> Status Type Value
     * @param prcTypVal -> Process Type value
     * @return -> List<OCmnMvrS>
     */

    List<OCmnMvrS> dcmTbl(String lngVal, String usrVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal,
	    Integer thpAcvVal, List<String> mvmIdn, Long qryDat, String mvmSttTypVal, String mvmPbl,
	    List<String> stsTypVal, String prcTypVal);

}
