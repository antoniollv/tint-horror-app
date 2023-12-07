package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.crn.dl.IDlCrnDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldCrn;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The currency validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 6 Jul 2021 - 13:30:21
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldCrnImpl implements IVldCrn {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The currency respository.*/
    @Autowired
    protected IDlCrnDAO iDlCnrDAO;

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
    public OTrnErrS vldCrnVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal crnVal, final Date vldDat,
            final BigDecimal lobVal) {

        try {
            int rows = iDlCnrDAO.count(cmpVal, lngVal, crnVal, vldDat, lobVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlCnrDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "CRN", "CRN_VAL", cmpVal);
         
    }

}
