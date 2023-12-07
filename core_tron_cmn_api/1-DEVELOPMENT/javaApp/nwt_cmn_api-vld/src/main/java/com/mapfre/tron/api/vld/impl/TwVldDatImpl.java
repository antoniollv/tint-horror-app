package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dat.dl.IDlDatDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldDat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The date validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 21 Jul 2021 - 14:18:05
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldDatImpl implements IVldDat {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The date respository.*/
    @Autowired
    protected IDlDatDAO iDlVldDAO;

    /**
     * Validate whether validation date exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Business line code
     * @param cvrVal -> Coverage code
     * @param mdtVal -> Modality code
     * @param vldDat -> Validation date
     * @return       -> An error if the validation date does not exit
     */
    @Override
    public OTrnErrS vldDat(final BigDecimal cmpVal, final String lngVal, final BigDecimal lobVal,
            final BigDecimal cvrVal, final BigDecimal mdtVal, final Date vldDat) {

        try {
            int rows = iDlVldDAO.count(cmpVal, lngVal, lobVal, cvrVal, mdtVal, vldDat);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlVldDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "DAT", "VLD_DAT", cmpVal);
         
    }

}
