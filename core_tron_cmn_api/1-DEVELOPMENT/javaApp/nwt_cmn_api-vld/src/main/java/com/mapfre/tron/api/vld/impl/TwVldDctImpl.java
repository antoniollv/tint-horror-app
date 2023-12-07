package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dct.dl.IDlDctDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldDct;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The state validation implementation.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 27 Jul 2021 - 09:33:29
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldDctImpl implements IVldDct {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The state respository.*/
    @Autowired
    protected IDlDctDAO iDlDctDAO;

    /**
     * Validate whether the value exists.
     *
     * @return       -> An error if the value does not exit
     */
    @Override
    public OTrnErrS vldDct(BigDecimal cmpVal, String lngVal, BigDecimal dctVal, BigDecimal crnVal, BigDecimal dctTypVal){

        try {
            int rows = iDlDctDAO.count(cmpVal, lngVal, dctVal, crnVal, dctTypVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlTypDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "DCT", "DCT_TYP_VAL", cmpVal);
         
    }

}
