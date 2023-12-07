package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The field validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 08:15:28
 *
 */
public interface IVldFld {

    /**
     * Validate whether the field value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Business line code
     * @param mdtVal -> Modality code
     * @param fldNam -> Field name
     * @return       -> An error if the field value does not exit
     */
    OTrnErrS vldFldVal(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, BigDecimal mdtVal, String fldNam);

}
