package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.thr.dl.IDlThrLvlDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldThr;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The third level validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 10:43:24
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldThrImpl implements IVldThr {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The Third level respository.*/
    @Autowired
    protected IDlThrLvlDAO iDlThrLvlDAO;

    /**
     * Validate whether third level value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thrLvlVal -> Third level code
     * @return          -> An error if the third level value does not exit
     */
    @Override
    public OTrnErrS vldThrLvl(final BigDecimal cmpVal, final String lngVal, final BigDecimal thrLvlVal) {

        try {
            int rows = iDlThrLvlDAO.count(cmpVal, lngVal, thrLvlVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlThrLvlDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "THR", "THR_LVL_VAL", cmpVal);
         
    }

}
