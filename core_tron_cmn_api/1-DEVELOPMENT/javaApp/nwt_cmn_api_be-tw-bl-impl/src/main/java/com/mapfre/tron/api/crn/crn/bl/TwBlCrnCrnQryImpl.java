package com.mapfre.tron.api.crn.crn.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnP;
import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * TwBlCrnCrnQryImpl
 *
 * @author pvraul1
 * @since 1.8
 * @version 17 oct. 2019 12:54:02
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCrnCrnQryImpl extends TwBlCmnBase implements IBlCrnCrnQry {

    /**
     * Query currencies. It will return the list of currencies.
     *
     * @author pvraul1
     *
     * @return Currency List
     */
    @Override
    public List<OCrnCrnP> tbl(final String lngVal, final Integer cmpVal) {
        log.debug("Tronweb business logic implementation TBL has been called...");

        // reseting session
        resetSession();

        BigDecimal bdCmpVal = null;

        if (cmpVal == null) {
            bdCmpVal = cmpValDefault;
        } else {
            bdCmpVal = new BigDecimal(cmpVal);
        }

        try {
            List<OCrnCrnP> lvOCrnCrnPList = cacheableAttribute.getOCrnCrnSAll(lngVal, bdCmpVal);

            if (lvOCrnCrnPList == null || lvOCrnCrnPList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCrnCrnPList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "CRN", bdCmpVal);
        }

        return Collections.emptyList();
    }

    @Override
    public OCrnCrnS getCurrency(String usrVal, String lngVal, Integer cmpVal, String sdrCrnVal) {
        log.debug("Tronweb business logic implementation getCurrency has been called...");

        // reseting session
        resetSession();

        BigDecimal pmCmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {
            OCrnCrnS lvOCrnCrnS = cacheableAttribute.getOCrnCrnSCurrency(lngVal, pmCmpVal, sdrCrnVal);

            if (lvOCrnCrnS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCrnCrnS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "CRN", pmCmpVal);
        }

        return null;
    }

}
