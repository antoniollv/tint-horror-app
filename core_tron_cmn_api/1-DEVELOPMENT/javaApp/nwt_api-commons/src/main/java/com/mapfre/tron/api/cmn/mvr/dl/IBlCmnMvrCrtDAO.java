package com.mapfre.tron.api.cmn.mvr.dl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;

public interface IBlCmnMvrCrtDAO {


    /**
     * Create movement record for a third party
     * 
     * @author Cristian Saball
     * 
     * @param lngVal           -> Language code
     * @param usrVal           -> User code
     * @param cmpVal           -> Company code
     * @param thpDcmTypVal     ->Document type
     * @param thpDcmVal        -> Document
     * @param thpAcvVal        -> Activity
     * @param thpAcvVal        -> Activity
     * @param inMovementRecord -> Input data to new movement record
     * @return -> void
     */
    int mvrOchUpdate(String lngVal, String usrVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal,
            Integer thpAcvVal, OCmnMvrS inMovementRecord);

    int mvrOchInsert(String lngVal, String usrVal, Integer cmpVal, String thpDcmTypVal, String thpDcmVal,
            Integer thpAcvVal, OCmnMvrS inMovementRecord);

    /**
     * Update rl_cmn_nwt_xx_mvr.
     *
     * @param cmpVal    -> Company code
     * @param rskIdn    -> The rskIdn property
     * @param sqnVal    -> The sqnVal property
     * @param errTxtVal -> The error txt value
     * @param stsTypVal -> The sts typ value
     * @param mvmPbl    -> The mvmPbl property
     * @return          -> The result of the update
     */
    int rlCmnNwtXxMvrUpdate(BigDecimal cmpVal, String rskIdn, BigDecimal sqnVal, String errTxtVal,
            String stsTypVal, String mvmPbl);

}
