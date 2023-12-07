package com.mapfre.tron.api.prt.sbs.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The TronWeb BlPrtSbsQry business logic service implementation.
 *
 * @author Cristian Saball
 * @version 30 agosto 2021
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlPrtSbsQryImpl extends TwBlCmnBase implements IBlPrtSbsQry {

    @Override
    public List<OPrtSbsS> sbsTbl(final Integer cmpVal, final Integer secVal, final String lngVal, final String usrVal) {
        log.debug("Tronweb business logic implementation sbsTbl have been called...");

        // reseting session
        resetSession();

        BigDecimal pmCmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;
        BigDecimal pmSecVal = (secVal != null) ? new BigDecimal(secVal) : null;

        try {
            List<OPrtSbsS> lvOPrtSbsS = cacheableAttribute.getSubSectorList(pmCmpVal, pmSecVal, lngVal, usrVal);

            if (lvOPrtSbsS == null || lvOPrtSbsS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            for (OPrtSbsS oPrtSbsS : lvOPrtSbsS) {
                getSecNam(pmCmpVal, oPrtSbsS);
            }

            return lvOPrtSbsS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "SBS", pmCmpVal);
        }

        return Collections.emptyList();
    }

}
