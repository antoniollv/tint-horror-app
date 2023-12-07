package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.cla.dl.IDlClaDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldCla;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The clause validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 8 Jul 2021 - 08:32:29
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldClauImpl implements IVldCla {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository.*/
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The clause repository.*/
    @Autowired
    protected IDlClaDAO iDlClaDAO;

    /**
     * Validate whether currency value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param crnVal -> Currency code
     * @param vldDat -> Validation date
     * @param lobVal -> Line of Business
     * @return       -> An error if the currency value does not exit
     */
    @Override
    public OTrnErrS vldClaVal(final BigDecimal cmpVal, final String lngVal, final String claVal, final Date vldDat) {

        try {
            int rows = iDlClaDAO.count(cmpVal, lngVal, claVal, vldDat);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlClaDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "CLA", "CLA_VAL", cmpVal);
         
    }

}
