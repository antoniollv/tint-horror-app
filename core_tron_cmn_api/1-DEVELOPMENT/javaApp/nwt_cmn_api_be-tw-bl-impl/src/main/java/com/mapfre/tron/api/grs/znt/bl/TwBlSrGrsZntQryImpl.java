package com.mapfre.tron.api.grs.znt.bl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntCPT;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntP;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The Tronweb IBlGrsZntQry service implementation.
 *
 * @author pvraul1
 * @since 1.8
 * @version 6 ago. 2019 12:13:42
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlSrGrsZntQryImpl extends TwBlCmnBase implements IBlGrsZntQry {

    @Override
    @Transactional("transactionManagerTwDl")
    public OGrsZntCPT set(final Integer cmpVal, final String usrVal, final String lngVal, final String cnyVal) {

        log.info("Tronweb business logic implementation set have been called...");

        // reseting session
        resetSession();

        BigDecimal pmcmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {
            List<OGrsZntS> lOGrsZntS = cacheableAttribute.getAllZoneTwo(cnyVal, lngVal, pmcmpVal);

            if (lOGrsZntS == null || lOGrsZntS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            List<OGrsZntP> grsZntPT = lOGrsZntS.stream().map(TwBlSrGrsZntQryImpl::getOGrsZntP)
                    .collect(Collectors.toList());

            OGrsZntCPT oGrsZntCPT = new OGrsZntCPT();
            oGrsZntCPT.setOGrsZntPT(grsZntPT);

            return oGrsZntCPT;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZNT", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    @Override
    @Transactional("transactionManagerTwDl")
    public OGrsZntP cnyStt(final Integer cmpVal, final String usrVal, final String lngVal, final String cnyVal,
            final Integer sttVal) {

        log.info("Tronweb business logic implementation cnyStt have been called...");

        // reseting session
        resetSession();

        BigDecimal pmSttVal = (sttVal != null) ? new BigDecimal(sttVal) : null;
        BigDecimal pmcmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {
            OGrsZntS lvOGrsZntS = cacheableAttribute.getZoneTwo(pmcmpVal, usrVal, lngVal, cnyVal, pmSttVal);

            if (lvOGrsZntS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            OGrsZntP oGrsZntP = new OGrsZntP();
            oGrsZntP.setOGrsZntS(lvOGrsZntS);

            return oGrsZntP;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZNT", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    protected static OGrsZntP getOGrsZntP(OGrsZntS oGrsZntS) {
        OGrsZntP oGrsZntP = new OGrsZntP();
        oGrsZntP.setOGrsZntS(oGrsZntS);

        return oGrsZntP;
    }

}
