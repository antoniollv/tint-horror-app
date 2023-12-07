package com.mapfre.tron.api.tpd.prf.bl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.tpd.prf.bo.OTpdPrfS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.tpd.prf.dl.IDlTpdPrfDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb TpdPrf query business service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 Mar 2023 - 10:05:44
 *
 */
@Slf4j
@Service
public class TwBlTpdPrfQryImpl extends TwBlCmnBase implements IBlTpdPrfQry {

    /** The TpdPrf repository.*/
    @Autowired
    protected IDlTpdPrfDAO iDlTpdPrfDAO;

    /**
     * Query Profession. It will return the information of Profession.
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param prfVal -> Profession
     * @param cmpVal -> Company code
     * @return       -> The information of Profession
     */
    @Override
    public OTpdPrfS getProfession(final String usrVal, final String lngVal, final Integer prfVal, final Integer cmpVal) {

        if (log.isInfoEnabled()) {
            log.info("The tronweb getProfession service implementation had been called.");
        }

        // resetting session
        resetSession();

        try {
            return iDlTpdPrfDAO.getProfession(usrVal, lngVal, prfVal, cmpVal);
        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "PRF", new BigDecimal(cmpVal));
        }

        return null;
    }

}
