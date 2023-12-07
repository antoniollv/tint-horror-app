package com.mapfre.tron.api.cmn.cvr.dl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The coverage repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 8 Jul 2021 - 13:42:39
 *
 */
public interface IDlCvrDAO {

    /**
     * Count how many rows exists for a specific coverage code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param cvrVal -> Coverage code
     * @param vldDat -> Validation date
     * @param lobVal -> Line of Business
     * @param mdtVal -> Modality code
     * @return       -> Number of rows for a specific coverage code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal cvrVal, Date vldDat, BigDecimal lobVal, String mdtVal);

}
