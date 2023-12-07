package com.mapfre.tron.api.cmn.cmc.dl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The commission chart repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 15 Jul 2021 - 08:59:34
 *
 */
public interface IDlCmcDAO {

    /**
     * Count how many rows exists for a specific commission chart code.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param lobVal       -> Business line code
     * @param vldDat       -> Validation date
     * @param thpVal       -> Thirpard agent code
     * @param cmcVal       -> Commission chart code
     * @param frsDstHnlVal -> First distribution channel code
     * @param scnDstHnlVal -> Second distribution channel code
     * @param thrDstHnlVal -> Third distribution channel code
     * @param frsLvlVal    -> First level code
     * @param scnLvlVal    -> Second level code
     * @param thrLvlVal    -> Third level code
     * @return             -> Number of rows for a specific commission chart code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, Date vldDat, BigDecimal thpVal,
            BigDecimal cmcVal, String frsDstHnlVal, String scnDstHnlVal, String thrDstHnlVal, BigDecimal frsLvlVal,
            BigDecimal scnLvlVal, BigDecimal thrLvlVal);

}
