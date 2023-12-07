package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.atz.dl.IDlAtzDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldAtz;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The authorization validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 08:24:00
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldAtzImpl  implements IVldAtz {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The authorization respository.*/
    @Autowired
    protected IDlAtzDAO iDlAtzDAO;

    /**
     * Validate whether authorization level value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param vldAtzLvl -> Authorization level code
     * @return          -> An error if the authorization level value does not exit
     */
    @Override
    public OTrnErrS vldAtzLvl(final BigDecimal cmpVal, final String lngVal, final BigDecimal atzLvlVal) {

        try {
            int rows = iDlAtzDAO.count(cmpVal, lngVal, atzLvlVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlAtzDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "ATZ", "ATZ_LVL_VAL", cmpVal);
         
    }

}
