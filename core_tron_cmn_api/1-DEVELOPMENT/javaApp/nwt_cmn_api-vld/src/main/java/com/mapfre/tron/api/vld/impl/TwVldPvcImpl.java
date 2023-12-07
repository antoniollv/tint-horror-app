package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.pvc.dl.IDlPvcDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldPvc;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * The validation interface.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 16 Jul 2021 - 09:33:29
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldPvcImpl implements IVldPvc {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The state respository.*/
    @Autowired
    protected IDlPvcDAO iDlPvcDAO;

    /**
     * Validate whether state value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param pvcVal -> Provincia
     * @param cnyVal -> Pais
     * 
     * @return       -> An error if the third level value does not exit
     */
    @Override
    public OTrnErrS vldPvc(BigDecimal cmpVal, String lngVal, BigDecimal pvcVal, String cnyVal){

        try {
            int rows = iDlPvcDAO.count(cmpVal, lngVal, pvcVal, cnyVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlPvcDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "PVC", "PVC_VAL", cmpVal);
         
    }

}
