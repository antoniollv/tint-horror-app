package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.err.dl.IDlErrDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldErr;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The error validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 19 Jul 2021 - 08:46:49
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldErrImpl implements IVldErr {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The error respository.*/
    @Autowired
    protected IDlErrDAO iDlErrDAO;

    /**
     * Validate whether the error value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param errVal -> Error code
     * @return       -> An error if the error value does not exit
     */
    @Override
    public OTrnErrS vldErrVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal errVal) {

        try {
            int rows = iDlErrDAO.count(cmpVal, lngVal, errVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlErrDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "ERR", "ERR_VAL", cmpVal);
         
    }

}
