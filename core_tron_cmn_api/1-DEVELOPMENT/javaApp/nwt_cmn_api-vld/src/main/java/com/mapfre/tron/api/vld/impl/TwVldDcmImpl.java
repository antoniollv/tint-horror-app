package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.dcm.fld.dl.IDlDcmDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldDcm;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The documentum validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 16 Jul 2021 - 09:51:29
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldDcmImpl implements IVldDcm {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The documentum respository.*/
    @Autowired
    protected IDlDcmDAO iDlDcmDAO;

    /**
     * Validate whether the third person documentum value exists.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param thpDcmVal    -> Third person documentum number
     * @param thpDcmTypVal -> Third person documentum type
     * @return             -> An error if the third person documentum value does not exit
     */
    @Override
    public OTrnErrS vldThpDcmVal(final BigDecimal cmpVal, final String lngVal, final String thpDcmVal,
            final String thpDcmTypVal) {

        try {
            int rows = iDlDcmDAO.count(cmpVal, lngVal, thpDcmVal, thpDcmTypVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlDcmDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "DCM", "THP_DCM_VAL", cmpVal);
         
    }

}
