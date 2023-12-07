package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.acg.dl.IDlAcgDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldAcg;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The accounting validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 12 Jul 2021 - 08:42:27
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldAcgImpl implements IVldAcg {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The authorization respository.*/
    @Autowired
    protected IDlAcgDAO iDlAcgDAO;

    /**
     * Validate whether the accounting line of business value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param acgLobVal -> Accounting line of business code
     * @return          -> An error if the accounting line of business value does not exit
     */
    @Override
    public OTrnErrS vldAcgLobVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal acgLobVal) {

        try {
            int rows = iDlAcgDAO.count(cmpVal, lngVal, acgLobVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlAtzDAO.count: " + e.getMessage());
        }

        return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "ACG", "ACG_LOB_VAL", cmpVal);
        
    }

}
