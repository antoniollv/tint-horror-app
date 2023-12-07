package com.mapfre.tron.api.vld.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.cmc.dl.IDlCmcDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.vld.api.IVldCmc;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The commission chart validation implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 15 Jul 2021 - 08:41:38
 *
 */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class TwVldCmcImpl implements IVldCmc {

    /** The newtron nof found code. */
    protected static final int ERROR_CODE = 20001;

    /** The error respository */
    @Autowired
    protected DlTrnErr dlTrnErr;

    /** The commission chart respository.*/
    @Autowired
    protected IDlCmcDAO iDlCmcDAO;

    /**
     * Validate whether the commission chart value exists.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param lobVal       -> Business line code
     * @param vldDat       -> Validation date
     * @param thpVal       -> Thirpard agent code
     * @param cmcVal       -> Commission chart code
     * @param frsDstHnlVal -> First distribution channel code
     * @param scnDstHnlVal -> Second distribution channel code
     * @param thrDstHnlVal -> Third distribution channel code
     * @param frsLvlVal    -> First level code
     * @param scnLvlVal    -> Second level code
     * @param thrLvlVal    -> Third level code
     * @return             -> An error if the commission chart value does not exit
     */
    @Override
    public OTrnErrS vldFldVal(final BigDecimal cmpVal, final String lngVal, final BigDecimal lobVal, final Date vldDat,
            final BigDecimal thpVal, final BigDecimal cmcVal, final String frsDstHnlVal, final String scnDstHnlVal,
            final String thrDstHnlVal, final BigDecimal frsLvlVal, final BigDecimal scnLvlVal,
            final BigDecimal thrLvlVal) {


        try {
            int rows = iDlCmcDAO.count(cmpVal, lngVal, lobVal, vldDat, thpVal, cmcVal, frsDstHnlVal, scnDstHnlVal,
                    thrDstHnlVal, frsLvlVal, scnLvlVal, thrLvlVal);
            if (rows > 0) {
                return null;
            }
        } catch (DataAccessException e) {
            log.error("Error calling iDlCmcDAO.count: " + e.getMessage());
        }

         return dlTrnErr.getError(new BigDecimal(ERROR_CODE), lngVal, "CMC", "CMC_VAL", cmpVal);
         
    }

}
