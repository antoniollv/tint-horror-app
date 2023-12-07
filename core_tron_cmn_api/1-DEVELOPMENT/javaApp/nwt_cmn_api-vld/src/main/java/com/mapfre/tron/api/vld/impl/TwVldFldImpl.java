package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.fld.dl.IDlFldDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldFld;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The field validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 08:23:15
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldFldImpl implements IVldFld {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The authorization respository.*/
    @Autowired
    protected IDlFldDAO iDlFldDAO;

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
    @Override
    public OTrnErrS vldFldVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal lobVal,
            final BigDecimal mdtVal, final String fldNam) {

        try {
            int rows = iDlFldDAO.count(cmpVal, lngVal, lobVal, mdtVal, fldNam);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlFldDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "FLD", "FLD_NAM", cmpVal);
         
    }

}
