package com.mapfre.tron.api.cmn.fld.dl;

import java.math.BigDecimal;

/**
 * The field repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 08:28:13
 *
 */
public interface IDlFldDAO {

    /**
     * Count how many rows exists for a specific field name.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Business line code
     * @param mdtVal -> Modality code
     * @param fldNam -> Field name
     * @return       -> Number of rows for a specific field name
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, BigDecimal mdtVal, String fldNam);

}
