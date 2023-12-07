package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.cmp.dl.IDlCmpDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldCmp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The company validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 13:07:32
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldCmpImpl implements IVldCmp {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The authorization respository.*/
    @Autowired
    protected IDlCmpDAO iDlCmpDAO;

    /**
     * Validate whether the company value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @return       -> An error if the company value does not exit
     */
    @Override
    public OTrnErrS vldCmpVal(final BigDecimal cmpVal, final String lngVal) {

        try {
            int rows = iDlCmpDAO.count(cmpVal, lngVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlCmpDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "COD", "CMP_VAL", cmpVal);
         
    }

}
