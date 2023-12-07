package com.mapfre.tron.api.trn.evn.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.evn.bo.OTrnEvnS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.trn.evn.dl.ISrlTrnEvnQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The TronWeb BlTrnEvnQry business logic service implementation.
 *
 * @author Cristian Saball
 * @version 31 ener. 2021
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlTrnEvnQryImpl extends TwBlCmnBase implements IBlTrnEvnQry {

    /** The SrlTrnEvnQry repository. */
    @Autowired
    protected ISrlTrnEvnQryDAO iSrlTrnEvnQryDAO;

    /**
     * Query message list for event not processed.
     *
     * @author Cristian Saball
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param qryDat    -> Query Date
     * @param evnIdnVal -> Event Identifier
     * @return          -> The message for event
     */
    @Override
    public List<OTrnEvnS> getQueryEventNotProcessed(final Integer cmpVal, final String lngVal, final String usrVal,
            final Long qryDat, final Integer evnIdnVal) {

        log.debug("Tronweb business logic implementation getQueryEventNotProcessed have been called...");

        // reseting session
        resetSession();

        BigDecimal cmpValValue = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {
            List<OTrnEvnS> lvOTrnEvnSList = iSrlTrnEvnQryDAO.getQueryEventNotProcessed(cmpVal, lngVal, usrVal, qryDat,
                    evnIdnVal);

            if (lvOTrnEvnSList == null || lvOTrnEvnSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOTrnEvnSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "EVN", cmpValValue);
        }

        return Collections.emptyList();
    }

}
