package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.mdl.dl.IDlMdlDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldMdl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The state validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 9 Jul 2021 - 09:36:22
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldMdlImpl implements IVldMdl {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The state respository.*/
    @Autowired
    protected IDlMdlDAO iDlMdlDAO;

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
    public OTrnErrS vldMdl(BigDecimal cmpVal, String lngVal, Date vldDat, BigDecimal makVal, BigDecimal mdlVal){

        try {
            int rows = iDlMdlDAO.count(cmpVal, lngVal, vldDat, makVal, mdlVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlSttDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "MDL", "MDL_VAL", cmpVal);
         
    }

}
