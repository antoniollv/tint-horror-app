package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The endorsement validation interface.
 *
 * @author majoguam
 * @since 1.8
 *
 */
public interface IVldEnr {

    /**
     * Validate Supplement value exists.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param enrVal       -> Endorsement code
     * @param enrSbdVal    -> Endorsement subcode
     * @param enrScoTypVal -> Endorsement scope type value
     * @return             -> An error if the Supplement value does not exit
     */
    OTrnErrS vldEnrVal(BigDecimal cmpVal, String lngVal, BigDecimal enrVal, BigDecimal enrSbdVal,
            BigDecimal enrScoTypVal);

    /**
     * Validate whether the endorsement subcode value exists.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param enrVal       -> Endorsement code
     * @param enrSbdVal    -> Endorsement subcode value
     * @param enrScoTypVal -> Endorsement scope type value
     * @return             -> An error if the endorsement subcode value does not exit
     */
    OTrnErrS vldEnrSbdVal(BigDecimal cmpVal, String lngVal, BigDecimal enrVal, BigDecimal enrSbdVal,
            String enrScoTypVal);

}
