package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The commission chart validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 15 Jul 2021 - 08:15:47
 *
 */
public interface IVldCmc {

    /**
     * Validate whether the commission chart value exists.
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
     * @return             -> An error if the commission chart value does not exit
     */
    OTrnErrS vldFldVal(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, Date vldDat, BigDecimal thpVal,
            BigDecimal cmcVal, String frsDstHnlVal, String scnDstHnlVal, String thrDstHnlVal, BigDecimal frsLvlVal,
            BigDecimal scnLvlVal, BigDecimal thrLvlVal);

}
