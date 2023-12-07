package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.typ.dl.IDlTypDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldTyp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The state validation implementation.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:33:29
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldTypImpl implements IVldTyp {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The state respository.*/
    @Autowired
    protected IDlTypDAO iDlTypDAO;

    /**
     * Validate whether the value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Ramo
     * @param valVal -> Valor
     * 
     * @return       -> An error if the third level value does not exit
     */
    @Override
    public OTrnErrS vldTyp(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, String valVal){

        try {
            int rows = iDlTypDAO.count(cmpVal, lngVal, lobVal, valVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlTypDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "TYP", "ISU_TYP_VAL", cmpVal);
         
    }

    
    /**
     * Validate type document  exists.
     *
     * @param cmpVal 		-> Company code
     * @param lngVal 		-> Language code
     * @param thpDcmTypVal 	-> third party type document value
     * 
     * @return       		-> An error if the third level value does not exit
     */
	@Override
	public OTrnErrS vldTypDoc(BigDecimal cmpVal, String lngVal, String thpDcmTypVal) {
		try {
            int rows = iDlTypDAO.countDoc(cmpVal, lngVal, thpDcmTypVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlTypDAO.countDoc: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "TYP", "THP_DCM_TYP_VAL", cmpVal);
         
	}


	
	/**
     * Validate Endorsement exists.
     *
     * @param cmpVal 		-> Company code
     * @param lngVal 		-> Language code
     * @param lobVal 		-> cod ramo
     * @param valVal 		-> value
     * 
     * @return       		-> An error if the third level value does not exit
     */
	@Override
	public OTrnErrS vldEnrTypVal(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, String valVal) {
		try {
            int rows = iDlTypDAO.countEnr(cmpVal, lngVal, lobVal, valVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlTypDAO.countEnr: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "TYP", "ENR_TYP_VAL", cmpVal);
         
	}

}
