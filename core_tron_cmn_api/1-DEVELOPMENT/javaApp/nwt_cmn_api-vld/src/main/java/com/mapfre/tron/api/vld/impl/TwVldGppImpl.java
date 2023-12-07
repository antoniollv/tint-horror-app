package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.gpp.dl.IDlGppDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldGpp;

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
public class TwVldGppImpl implements IVldGpp {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The authorization respository.*/
    @Autowired
    protected IDlGppDAO iDlGppDAO;

    /**
     * Validate Supplement value exists.
     *
     * @param cmpVal    	-> Company code
     * @param lngVal    	-> Language code
     * @param enrVal 		-> Endorsement code
     * @param enrSbdVal 	-> Endorsement subcode
     * @param enrScoTypVal 	-> Endorsement scope type value
     * @return          	-> An error if the Supplement value does not exit
     */

    @Override
    public OTrnErrS vldGppVal(final BigDecimal cmpVal, final String lngVal, final String gppVal) {

        try {
            int rows = iDlGppDAO.count(cmpVal, lngVal, gppVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlAtzDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "GPP", "GPP_VAL", cmpVal);
         
    }

}
