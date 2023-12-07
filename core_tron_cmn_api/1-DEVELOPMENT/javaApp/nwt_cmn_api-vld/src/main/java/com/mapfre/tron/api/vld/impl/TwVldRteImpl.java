package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.rte.dl.IDlRteDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldRte;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The rate validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 19 Jul 2021 - 09:47:41
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldRteImpl implements IVldRte {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The rate respository.*/
    @Autowired
    protected IDlRteDAO iDlRteDAO;

    /**
     * Validate whether the rate value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param rteVal -> Rate code
     * @return       -> An error if the rate value does not exit
     */
    @Override
    public OTrnErrS vldRteVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal rteVal) {

        try {
            int rows = iDlRteDAO.count(cmpVal, lngVal, rteVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlErrDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "RTE", "RTE_VAL", cmpVal);
         
    }

}
