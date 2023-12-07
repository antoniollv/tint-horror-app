
package com.mapfre.tron.api.cmn.col.bl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.cmn.col.bo.OCmnColCPT;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColP;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColS;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb BLCmnColQry business service implementation.
 *
 * @author pvraul1
 * @since 1.8
 * @version 6 ago. 2019 12:41:13
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmnColQryImpl extends TwBlCmnBase implements IBlCmnColQry {

    @Override
    @Transactional("transactionManagerTwDl")
    public OCmnColP col(final Integer cmpVal, final String usrVal, final String lngVal, final Integer colVal) {
        log.info("Tronweb business logic implementation col have been called...");

        // reseting session
        resetSession();

        try {
            OCmnColP lvOCmnColP = cacheableAttribute.getColor(BigDecimal.valueOf(colVal), lngVal,
                    BigDecimal.valueOf(cmpVal));

            if (lvOCmnColP == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCmnColP;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "COL", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    @Override
    @Transactional("transactionManagerTwDl")
    public OCmnColCPT set(final Integer cmpVal, final String usrVal, final String lngVal) {
        log.info("Tronweb business logic implementation set have been called...");

        // reseting session
        resetSession();

        try {
            OCmnColCPT oCmnColCPT = new OCmnColCPT();
            List<OCmnColS> lOCmnColS = cacheableAttribute.getAllColours(lngVal, BigDecimal.valueOf(cmpVal));

            if (lOCmnColS == null || lOCmnColS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            List<OCmnColP> oCmnColPT = lOCmnColS.stream().map(TwBlCmnColQryImpl::oCmnColSToOCmnColP)
                    .collect(Collectors.toList());

            oCmnColCPT.setOCmnColPT(oCmnColPT);
            oCmnColCPT.setPrcTrmVal("");

            return oCmnColCPT;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "COL", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    public static OCmnColP oCmnColSToOCmnColP(OCmnColS oCmnColS) {
        OCmnColP oCmnColP = new OCmnColP();

        oCmnColP.setOCmnColS(oCmnColS);
        oCmnColP.setOTrnPrcS(new OTrnPrcS());

        return oCmnColP;
    }

}
