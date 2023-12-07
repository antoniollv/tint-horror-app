package com.mapfre.tron.api.cmn.trn.evs;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.evn.bo.OTrnEvnS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author AMINJOU
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmnTrnEvsQryImpl extends TwBlCmnBase implements IBlCmnTrnEvsQry {

    @Autowired
    protected IDlTrnEvsDAO iDlTrnEvsDAO;

    /**
     * update received event as processed
     * 
     * @param cmpVal
     * @param lngVal
     * @param usrVal
     * @param oTrnEvnS
     * 
     */
    @Override
    public void setUpdateEvent(final Integer cmpVal, final String lngVal, final String usrVal,
            final OTrnEvnS oTrnEvnS) {

        log.debug("Tronweb business logic implementation setUpdateEvent have been called...");

        resetSession();

        BigDecimal cmpValValue = (cmpVal != null) ? new BigDecimal(cmpVal) : cmpValDefault;

        try {
            int rowUpdated = iDlTrnEvsDAO.setUpdateEvent(cmpValValue, lngVal, usrVal, oTrnEvnS);

            if (rowUpdated == 0) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "EVN", cmpValValue);
        }

    }

}
