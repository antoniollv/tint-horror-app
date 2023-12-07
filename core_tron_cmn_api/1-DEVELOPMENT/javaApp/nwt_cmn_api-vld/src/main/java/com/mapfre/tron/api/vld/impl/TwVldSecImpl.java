package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.sec.dl.IDlSecDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldSec;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The sector validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 12 Jul 2021 - 12:10:57
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldSecImpl implements IVldSec {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The authorization respository.*/
    @Autowired
    protected IDlSecDAO iDlSecDAO;

    /**
     * Validate whether the sector value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param secVal -> Sector code
     * @return       -> An error if the sector value does not exit
     */
    @Override
    public OTrnErrS vldSecVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal secVal) {

        try {
            int rows = iDlSecDAO.count(cmpVal, lngVal, secVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlSecDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "SEC", "SEC_VAL", cmpVal);
         
    }

}
