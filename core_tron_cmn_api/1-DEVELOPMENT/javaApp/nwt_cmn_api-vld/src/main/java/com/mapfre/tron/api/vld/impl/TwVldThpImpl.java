package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.thp.dl.IDlThpAcvDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldThp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb thirdpart validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 2 Jul 2021 - 11:42:36
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldThpImpl implements IVldThp {

    protected static final String THP_VAL = "THP_VAL";

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The thirdpart activity repository.*/
    @Autowired
    protected IDlThpAcvDAO iDlThpAcvDAO;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /**
     * Validate whether thirpart activity value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpAcvVal -> Thirdpart activity code
     * @return          -> An error if the thirdpart activite value does not exit
     */
    @Override
    public OTrnErrS vldThpAcvVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal thpAcvVal) {
        try {
            int rows = iDlThpAcvDAO.count(cmpVal, lngVal, thpAcvVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlThpAcvDAO.count:  " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "ACV", "THP_ACV_VAL", cmpVal);
         
    }

    /**
     * Validate whether thirpart agent code value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param thpVal -> Thirpard agent code
     * @param vldDat -> Validation date
     * @return       -> An error if the thirdpart agent code value does not exit
     */
    @Override
    public OTrnErrS vldThpVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal thpVal,
            final Date vldDat) {

        try {
            int rows = iDlThpAcvDAO.count(cmpVal, lngVal, thpVal, vldDat);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlThpAcvDAO.count:   " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "AGT", THP_VAL, cmpVal);
         
    }

    /**
     * Validate whether thirpart code value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thpVal    -> Thirpard code
     * @param thpAcvVal -> Thirdpart activity code
     * @return          -> An error if the thirdpart code value does not exit
     */
    @Override
    public OTrnErrS vldThpVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal thpVal,
            final BigDecimal thpAcvVal) {

        try {
            int rows = iDlThpAcvDAO.count(cmpVal, lngVal, thpVal, thpAcvVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlThpAcvDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "THP", THP_VAL, cmpVal);
         
    }

	@Override
	public OTrnErrS vldThpValTramitador(BigDecimal cmpVal, String lngVal, BigDecimal thpVal) {
        try {
            int rows = iDlThpAcvDAO.countTramitador(cmpVal, lngVal, thpVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlThpAcvDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "THP", THP_VAL, cmpVal);
         
	}

}
