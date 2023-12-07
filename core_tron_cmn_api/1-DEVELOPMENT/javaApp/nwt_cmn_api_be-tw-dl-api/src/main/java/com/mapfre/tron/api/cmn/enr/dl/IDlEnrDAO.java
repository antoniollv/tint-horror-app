package com.mapfre.tron.api.cmn.enr.dl;

import java.math.BigDecimal;

/**
 * The endorsement repository.
 *
 * @author majoguam
 * @since 1.8
 *
 */
public interface IDlEnrDAO {

    /**
     * Count how many rows exists for a specific endorsement code.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param enrVal       -> Endorsement code
     * @param enrSbdVal    -> Endorsement subcode value
     * @param enrScoTypVal -> Endorsement scope type value
     * @return             -> Number of rows for a specific endorsement code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal enrVal, BigDecimal enrSbdVal, BigDecimal enrScoTypVal);

    /**
     * Count how many rows exists for a specific endorsement subcode.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param enrVal       -> Endorsement code
     * @param enrSbdVal    -> Endorsement subcode value
     * @param enrScoTypVal -> Endorsement scope type value
     * @param temVal       -> Termine value
     * @return             -> Number of rows for a specific endorsement subcode
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal enrVal, BigDecimal enrSbdVal, String enrScoTypVal,
            String temVal);

}
