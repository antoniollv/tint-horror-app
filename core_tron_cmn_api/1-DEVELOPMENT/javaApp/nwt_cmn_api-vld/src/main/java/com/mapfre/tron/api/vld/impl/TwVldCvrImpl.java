package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.cvr.dl.IDlCvrDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldCvr;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * General class comment.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 8 Jul 2021 - 13:37:58
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldCvrImpl implements IVldCvr {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository.*/
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The coverage repository.*/
    @Autowired
    protected IDlCvrDAO iDlCvrDAO;

    /**
     * Validate whether coverage value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param cvrVal -> Coverage code
     * @param vldDat -> Validation date
     * @param lobVal -> Line of Business
     * @param mdtVal -> Modality code
     * @return       -> An error if the coverage value does not exit
     */
    @Override
    public OTrnErrS vldCvrVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal cvrVal, final Date vldDat,
            final BigDecimal lobVal, final String mdtVal) {

        try {
            int rows = iDlCvrDAO.count(cmpVal, lngVal, cvrVal, vldDat, lobVal, mdtVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlCnrDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "CVR", "CVR_VAL", cmpVal);
         
    }

}
