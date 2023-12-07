package com.mapfre.tron.api.cmn.aed.bl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;
import com.mapfre.tron.api.cmn.aed.dl.IDlCmnAedDAO;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.extern.slf4j.Slf4j;

/**
 * The trowneb CmnAedQry business logic implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 17 Oct 2023 - 09:58:37
 *
 */
@Slf4j
@Service
public class TwBlCmnAedQryImpl extends TwBlCmnBase implements IBlCmnAedQry {

    /** The CmnAed repository.*/
    @Autowired
    protected IDlCmnAedDAO iDlCmnAedDAO;

    /**
     * TRON-16952 Query data access by user code.
     *
     * @param cmpVal    -> Company Code
     * @param cmpUsrVal -> Company User Value
     * @param lngVal    -> Language Code
     * @param usrVal    -> User Value
     * @return          -> The data access
     */
    @Override
    public OCmnAedS getDataAccessUser(final Integer cmpVal, final String cmpUsrVal, final String lngVal,
            final String usrVal) {

        if (log.isInfoEnabled()) {
            log.info("Tw getDataAccessUser business service called!");
        }

        // reseting session
        resetSession();

        try {
            return iDlCmnAedDAO.get000(cmpVal, cmpUsrVal, lngVal, usrVal);
        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "AED", new BigDecimal(cmpVal));
        }

        return null;
    }

}
