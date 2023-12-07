package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.cnc.dl.IDlCncDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldCnc;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The concept validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 14 Jul 2021 - 11:23:11
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldCncImpl implements IVldCnc {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The authorization respository.*/
    @Autowired
    protected IDlCncDAO iDlCncDAO;

    /**
     * Validate whether the reserve concept value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param rsvCncVal -> Reserve concept code
     * @return          -> An error if the reserve concept value does not exit
     */
    @Override
    public OTrnErrS vldRsvCncVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal rsvCncVal) {

        try {
            int rows = iDlCncDAO.count(cmpVal, lngVal, rsvCncVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling rsvCncVal.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "CNC", "RSV_CNC_VAL", cmpVal);
         
    }

}
