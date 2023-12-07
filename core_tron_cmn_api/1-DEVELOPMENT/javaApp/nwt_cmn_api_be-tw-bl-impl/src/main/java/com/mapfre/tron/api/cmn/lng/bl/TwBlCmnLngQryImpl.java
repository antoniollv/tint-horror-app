package com.mapfre.tron.api.cmn.lng.bl;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngCPT;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngP;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author SSROBE1
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmnLngQryImpl extends TwBlCmnBase implements IBlCmnLngQry {

    @Override
    @Transactional("transactionManagerTwDl")
    public OCmnLngCPT getTbl(final String lngVal, final String usrVal) {
        log.debug("Tronweb business logic implementation getTbl have been called...");

        // reseting session
        resetSession();

        try {
            List<OCmnLngP> lvOCmnLngP = cacheableAttribute.getAllLanguages(lngVal, cmpValDefault);

            if (lvOCmnLngP == null || lvOCmnLngP.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            OCmnLngCPT lvOCmnLngCPT = new OCmnLngCPT();
            lvOCmnLngCPT.setOCmnLngPT(lvOCmnLngP);

            return lvOCmnLngCPT;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "LNG", cmpValDefault);
        }

        return null;
    }

    @Override
    public OCmnLngS getLng(final String lngVal, final String usrVal) {
        log.debug("Tronweb business logic implementation getLng have been called...");

        // reseting session
        resetSession();

        try {
            OCmnLngS lvOCmnLngS = cacheableAttribute.getLanguage(lngVal, cmpValDefault);

            if (lvOCmnLngS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCmnLngS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "LNG", cmpValDefault);
        }

        return null;
    }

}
