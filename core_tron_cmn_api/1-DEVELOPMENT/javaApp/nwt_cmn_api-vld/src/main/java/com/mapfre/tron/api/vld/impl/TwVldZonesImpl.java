package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.vld.dl.IDlZonesDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldZones;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb zones validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 5 Jul 2021 - 11:01:10
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldZonesImpl implements IVldZones {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The validation zones repository.*/
    @Autowired
    protected IDlZonesDAO iDlZonesDAO;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /**
     * Validate whether postal code value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country value
     * @param pslCodVal -> Postal code
     * @param twnVal    -> Town Value
     *
     * @return -> An error if the postal code value does not exit
     */
    @Override
    public OTrnErrS vldZoneFour(final BigDecimal cmpVal, final String lngVal, final String cnyVal,
            final BigDecimal pslCodVal, final BigDecimal twnVal) {

        try {
            int rows = iDlZonesDAO.count(cmpVal, lngVal, cnyVal, pslCodVal, twnVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlZonesDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "PSL", "COD_POSTAL", cmpVal);
         
    }

}
