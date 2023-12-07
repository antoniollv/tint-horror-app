package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.cny.dl.IDlCnyDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldCny;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The country validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 13:19:44
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldCnyImpl implements IVldCny {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The country respository.*/
    @Autowired
    protected IDlCnyDAO iDlCnyDAO;

    /**
     * Validate whether country value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param cnyVal -> Country code
     * @return       -> An error if the country value does not exit
     */
    @Override
    public OTrnErrS vldThrLvl(final BigDecimal cmpVal, final String lngVal, final String cnyVal) {

        try {
            int rows = iDlCnyDAO.count(cmpVal, lngVal, cnyVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlCnyDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "CNY", "CNY_VAL", cmpVal);
         
    }

}
