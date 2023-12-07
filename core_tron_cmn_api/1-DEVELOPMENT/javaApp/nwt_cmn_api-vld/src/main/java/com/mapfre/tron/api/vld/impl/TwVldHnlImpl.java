package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.hnl.dl.IDlHnlDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldHnl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The channel validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 09:30:56
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldHnlImpl implements IVldHnl {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The authorization respository.*/
    @Autowired
    protected IDlHnlDAO iDlHnlDAO;

    /**
     * Validate whether the third distribution channel value exists.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param thdDstHnlVal -> Third distribution channel code
     * @return             -> An error if the third distribution channel value does not exit
     */
    @Override
    public OTrnErrS vldThrDstHnlVal(final BigDecimal cmpVal, final String lngVal, final String thdDstHnlVal) {

        try {
            int rows = iDlHnlDAO.count(cmpVal, lngVal, thdDstHnlVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlHnlDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "HNL", "THR_DST_HNL_VAL", cmpVal);
         
    }

}
