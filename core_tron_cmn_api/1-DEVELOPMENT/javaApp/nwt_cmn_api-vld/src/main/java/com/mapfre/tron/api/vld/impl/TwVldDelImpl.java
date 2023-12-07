package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.del.dl.IDlDelDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldDel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The accounting validation implementation.
 *
 * @author  majoguam
 * @since 1.8
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldDelImpl implements IVldDel {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The authorization respository.*/
    @Autowired
    protected IDlDelDAO iDlDelDAO;

    /**
     * Validate del_val value exists.
     *
     * @param cmpVal    	-> Company code
     * @param lngVal    	-> Language code
     * @param lobVal 		-> Line of Business
     * @param vldDat 		-> Validation Date
     * @param delVal 		-> 
     * @return          	-> An error if the Supplement value does not exit
     */
	@Override
	public OTrnErrS vldDelVal(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, Date vldDat, BigDecimal delVal) {
        try {
            int rows = iDlDelDAO.count(cmpVal, lngVal, lobVal, vldDat, delVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlAtzDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "DEL", "DEL_VAL", cmpVal);
         
	}

}
