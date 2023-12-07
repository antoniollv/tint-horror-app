
package com.mapfre.tron.api.grs.znf.bl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfCPT;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfP;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb IBlGrsZnfQry business service implementation.
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
public class TwBlGrsZnfQryImpl extends TwBlCmnBase implements IBlGrsZnfQry {

    @Override
    @Transactional("transactionManagerTwDl")
    public OGrsZnfCPT tbl(final Integer cmpVal, final String usrVal, final String lngVal, final String cnyVal,
            final Integer pvcVal) {

        log.info("Tronweb business logic implementation tbl have been called...");

        // reseting session
        resetSession();

        try {
            List<OGrsZnfS> lista = cacheableAttribute.getAllZoneFour(cnyVal, BigDecimal.valueOf(pvcVal), lngVal,
                    BigDecimal.valueOf(cmpVal));

            if (lista == null || lista.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            List<OGrsZnfP> grsZnfPT = lista.stream().map(TwBlGrsZnfQryImpl::getOGrsZnfP).collect((Collectors.toList()));

            OGrsZnfCPT oGrsZnfCPT = new OGrsZnfCPT();
            oGrsZnfCPT.setOGrsZnfPT(grsZnfPT);

            return oGrsZnfCPT;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZNF", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    @Override
    @Transactional("transactionManagerTwDl")
    public OGrsZnfS cnyPvcTwn(final Integer cmpVal, final String usrVal, final String lngVal, final String cnyVal,
            final Integer pvcVal, final Integer twnVal) {

        log.info("Tronweb business logic implementation cnyPvcTwn have been called...");

        // reseting session
        resetSession();

        BigDecimal pmPvcVal = (pvcVal != null) ? new BigDecimal(pvcVal) : null;
        BigDecimal pmTwnVal = (twnVal != null) ? new BigDecimal(twnVal) : null;
        BigDecimal pmcmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {

            OGrsZnfS lvOGrsZnfS = cacheableAttribute.getZoneFour(pmcmpVal, usrVal, lngVal, cnyVal, pmPvcVal, pmTwnVal);

            if (lvOGrsZnfS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZnfS;
        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZNF", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    protected static OGrsZnfP getOGrsZnfP(OGrsZnfS oGrsZnfS) {
        OGrsZnfP lvOGrsZnfP = new OGrsZnfP();
        lvOGrsZnfP.setOGrsZnfS(oGrsZnfS);

        return lvOGrsZnfP;
    }

}
