
package com.mapfre.tron.api.cmn.cmp.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb BlCmnCmpQry business service implementation.
 *
 * @author Javier Sangil
 * @version 13/09/2021
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmnCmpQryImpl extends TwBlCmnBase implements IBlCmnCmpQry {

    @Override
    @Transactional("transactionManagerTwDl")
    public List<OCmuCmpS> getCompanies(final String usrVal, final String lngVal) {
        log.debug("Tronweb business logic implementation getCompanies have been called...");

        // reseting session
        resetSession();

        try {
            List<OCmuCmpS> lvOCmuCmpS = cacheableAttribute.getAllCompanies(usrVal, lngVal);

            if (lvOCmuCmpS == null || lvOCmuCmpS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCmuCmpS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "CMP", cmpValDefault);
        }

        return Collections.emptyList();
    }

    @Override
    @Transactional("transactionManagerTwDl")
    public OCmuCmpS getCompany(final String usrVal, final String lngVal, final BigDecimal cmpVal) {
        log.debug("Tronweb business logic implementation getCompany have been called...");

        // reseting session
        resetSession();

        try {
            OCmuCmpS oCmuCmpS = cacheableAttribute.getCompany(usrVal, lngVal, cmpVal);

            if (oCmuCmpS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oCmuCmpS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "CMP", cmpVal);
        }

        return null;
    }

}
