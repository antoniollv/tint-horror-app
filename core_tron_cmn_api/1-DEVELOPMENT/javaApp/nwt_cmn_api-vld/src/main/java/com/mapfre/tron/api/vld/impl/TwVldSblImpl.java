package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.sbl.dl.IDlSblDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldSbl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The validation interface.
 *
 * @author architecture - AMINJOU
 * @since 1.8
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldSblImpl implements IVldSbl {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The state respository.*/
    @Autowired
    protected IDlSblDAO iDlSblDAO;

    /**
     * Validate whether the value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param sblVal -> 
     * 
     * @return       -> An error if the third level value does not exit
     */
	@Override
	public OTrnErrS vldSblVal(BigDecimal cmpVal, String lngVal, BigDecimal sblVal) {
        try {
            int rows = iDlSblDAO.count(cmpVal, lngVal, sblVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlSblDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "SBL", "SBL_VAL", cmpVal);
         
	}

}
