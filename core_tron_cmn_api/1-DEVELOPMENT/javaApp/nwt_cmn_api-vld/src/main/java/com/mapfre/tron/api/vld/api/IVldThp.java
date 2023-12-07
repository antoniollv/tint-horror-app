package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The Thirdpart Validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 2 Jul 2021 - 11:36:05
 *
 */
public interface IVldThp {

    /**
     * Validate whether thirpart activity value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpAcvVal -> Thirdpart activity code
     * @return          -> An error if the thirdpart activite value does not exit
     */
    OTrnErrS vldThpAcvVal(BigDecimal cmpVal, String lngVal, BigDecimal thpAcvVal);

    /**
     * Validate whether thirpart agent code value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param thpVal -> Thirpard agent code
     * @param vldDat -> Validation date
     * @return       -> An error if the thirdpart agent code value does not exit
     */
    OTrnErrS vldThpVal(BigDecimal cmpVal, String lngVal, BigDecimal thpVal, Date vldDat);

    /**
     * Validate whether thirpart code value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpVal    -> Thirpard code
     * @param thpAcvVal -> Thirdpart activity code
     * @return          -> An error if the thirdpart code value does not exit
     */
    OTrnErrS vldThpVal(BigDecimal cmpVal, String lngVal, BigDecimal thpVal, BigDecimal thpAcvVal);
    
    
    
    /**
     * Validate whether thirpart code value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpVal    -> Thirpard code
     * @return          -> An error if the thirdpart code value does not exit
     */
    OTrnErrS vldThpValTramitador(BigDecimal cmpVal, String lngVal, BigDecimal thpVal);

}
