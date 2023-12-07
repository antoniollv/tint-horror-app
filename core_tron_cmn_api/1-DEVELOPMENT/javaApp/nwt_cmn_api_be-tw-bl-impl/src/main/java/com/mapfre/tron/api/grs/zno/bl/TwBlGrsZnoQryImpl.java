
package com.mapfre.tron.api.grs.zno.bl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoCPT;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoP;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb CmnBL business service implementation.
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
public class TwBlGrsZnoQryImpl extends TwBlCmnBase implements IBlGrsZnoQry {

    @Override
    @Transactional("transactionManagerTwDl")
    public OGrsZnoS cny(final Integer cmpVal, final String usrVal, final String lngVal, final String cnyVal) {

        log.info("Tronweb business logic implementation cny have been called...");

        // reseting session
        resetSession();

        BigDecimal pmcmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {
            OGrsZnoS lvOGrsZnoS = cacheableAttribute.getZoneOne(pmcmpVal, usrVal, lngVal, cnyVal);

            if (lvOGrsZnoS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZnoS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZNO", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    @Override
    @Transactional("transactionManagerTwDl")
    public OGrsZnoCPT set(final Integer cmpVal, final String usrVal, final String lngVal) {

        log.info("Tronweb business logic implementation set have been called...");

        // reseting session
        resetSession();

        BigDecimal pmcmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {
            List<OGrsZnoS> oGrsZnoS = cacheableAttribute.getAllZoneOne(lngVal, pmcmpVal);

            if (oGrsZnoS == null || oGrsZnoS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            List<OGrsZnoP> grsZnoPT = oGrsZnoS.stream().map(TwBlGrsZnoQryImpl::getOGrsZnoP)
                    .collect(Collectors.toList());

            OGrsZnoCPT oGrsZnoCPT = new OGrsZnoCPT();
            oGrsZnoCPT.setPrcTrmVal(CTrn.TRM_VAL_OK);
            oGrsZnoCPT.setOGrsZnoPT(grsZnoPT);

            return oGrsZnoCPT;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZNO", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    protected static OGrsZnoP getOGrsZnoP(OGrsZnoS oGrsZnoS) {
        OGrsZnoP oGrsZnoP = new OGrsZnoP();
        oGrsZnoP.setOGrsZnoS(oGrsZnoS);

        return oGrsZnoP;
    }

}
