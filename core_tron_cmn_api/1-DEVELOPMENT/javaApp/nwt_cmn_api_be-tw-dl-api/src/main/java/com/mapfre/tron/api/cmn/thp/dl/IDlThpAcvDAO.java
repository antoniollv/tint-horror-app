package com.mapfre.tron.api.cmn.thp.dl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The ThpAcn repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 2 Jul 2021 - 11:24:39
 *
 */
public interface IDlThpAcvDAO {

    /**
     * Count how many rows exists for a specific thirdpart activity code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpAcvVal -> Thirdpart activity code
     * @return          -> Number of rows for a specific thirdpart activity code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal thpAcvVal);

    /**
     * Count how many rows exists for a specific thirdpart agent code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param thpVal -> Thirdpart agent code
     * @param vldDat -> Validation date
     * @return       -> Number of rows for a specific thirdpart agent code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal thpVal, Date vldDat);

    /**
     * Count how many rows exists for a specific thirdpart code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpVal    -> Thirdpart code
     * @param thpAcvVal -> Thirdpart activity code
     * @return          -> Number of rows for a specific thirdpart code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal thpVal, BigDecimal thpAcvVal);

    
    /**
     * Count how many rows exists for a specific thirdpart code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpVal    -> Thirdpart code
     * @return          -> Number of rows for a specific thirdpart code
     */
	int countTramitador(BigDecimal cmpVal, String lngVal, BigDecimal thpVal);

}
