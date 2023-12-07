package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.brw.dl.IDlBrwDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldBrw;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
*
* @author Javier Sangil
* @since 1.8
* @version 15 Jul 2021
*
*/
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldBrwImpl implements IVldBrw {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The state respository.*/
    @Autowired
    protected IDlBrwDAO iDlBrwDAO;

    /**
     * Validate whether state value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param vldDat -> Fecha validez
     * @param makVal -> Codigo marca
     * @param mdlVal -> Codigo modelo
     * @return       -> An error if the third level value does not exit
     */
    @Override
    public OTrnErrS vldBrw(BigDecimal cmpVal, String lngVal, Date vldDat, BigDecimal mdtVal, BigDecimal lobVal, BigDecimal ecmBrwCncVal, BigDecimal cvrVal){

        try {
            int rows = iDlBrwDAO.count(cmpVal, lngVal, vldDat, mdtVal, lobVal, ecmBrwCncVal, cvrVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlSttDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "BRW", "ECM_BRW_CNC_VAL", cmpVal);
         
    }

}
