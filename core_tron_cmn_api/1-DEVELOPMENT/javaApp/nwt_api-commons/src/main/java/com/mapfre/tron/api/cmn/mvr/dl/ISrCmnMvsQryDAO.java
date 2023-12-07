package com.mapfre.tron.api.cmn.mvr.dl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;

/**
 * @author Javier Sangil
 * @version 04/06/2021
 *
 */
public interface ISrCmnMvsQryDAO {

    /**
     * Query movements record by third party
     * 
     * @author Javier Sangil
     * 
     * @param lngVal           -> Language code
     * @param usrVal           -> User code
     * @param cmpVal           -> Company code
     * @param thpDcmTypVal     -> Document type
     * @param thpDcmVal        -> document
     * @param thpAcvVal        -> Activity
     * @param mvmIdn           -> Third Channel Distribution
     * @param qryDat           -> Initial Date
     * @param mvmSttTypVal     -> Movement state type
     * @param mvmPbl           -> Public Movement
     * @param stsTypVal        -> Status Type Value
     * @param prcTypVal        -> Process Type value
     * @param orderByAgrValPrn -> Order the query by field agrValPrn
     * @return -> List<OCmnMvrS>
     */
    public List<OCmnMvrS> dcmTbl(String lngVal, String usrVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal,
            Integer thpAcvVal, List<String> mvmIdn, Long qryDat, String mvmSttTypVal, String mvmPbl,
            List<String> stsTypVal, String prcTypVal, boolean orderByAgrValPrn);

}
