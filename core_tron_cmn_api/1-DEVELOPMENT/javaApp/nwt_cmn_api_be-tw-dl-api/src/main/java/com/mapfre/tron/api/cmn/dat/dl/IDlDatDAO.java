package com.mapfre.tron.api.cmn.dat.dl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The date repository interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 21 Jul 2021 - 14:21:52
 *
 */
public interface IDlDatDAO {

    /**
     * Count how many rows exists for a specific validation date.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Business line code
     * @param cvrVal -> Coverage code
     * @param mdtVal -> Modality code
     * @param vldDat -> Validation date
     * @return       -> Number of rows for a specific validation date
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, BigDecimal cvrVal, BigDecimal mdtVal,
            Date vldDat);

}
