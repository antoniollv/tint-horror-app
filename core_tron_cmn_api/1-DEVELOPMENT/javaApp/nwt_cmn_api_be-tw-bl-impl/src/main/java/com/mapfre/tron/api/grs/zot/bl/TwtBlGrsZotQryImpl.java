
package com.mapfre.tron.api.grs.zot.bl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotCPT;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotP;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The Tronweb IsuCmnBl service implementation.
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
public class TwtBlGrsZotQryImpl extends TwBlCmnBase implements IBlGrsZotQry {

    @Override
    public OGrsZotCPT zonZntSet(final Integer cmpVal, final String usrVal, final String lngVal, final String cnyVal,
            final Integer sttVal) {

        log.info("Tronweb business logic implementation zonZntSet have been called...");

        // reseting session
        resetSession();

        BigDecimal pmcmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;
        try {
            List<OGrsZotS> loGrsZotS = cacheableAttribute.getAllZoneThree(cnyVal, BigDecimal.valueOf(sttVal), lngVal,
                    pmcmpVal);

            if (loGrsZotS == null || loGrsZotS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            List<OGrsZotP> oGrsZotPT = loGrsZotS.stream().map(TwtBlGrsZotQryImpl::oGrsZotSToOGrsZotP)
                    .collect(Collectors.toList());

            if (oGrsZotPT != null && !oGrsZotPT.isEmpty()) {
                for (OGrsZotP oGrsZotP : oGrsZotPT) {
                    OGrsZnoS oGrsZnoS = cacheableAttribute.getZoneOne(pmcmpVal, usrVal, lngVal,
                            oGrsZotP.getOGrsZotS().getCnyVal());
                    oGrsZotP.getOGrsZotS().setCnyNam(oGrsZnoS.getCnyNam());
                    OGrsZntS grsZntS = cacheableAttribute.getZoneTwo(pmcmpVal, usrVal, lngVal,
                            oGrsZotP.getOGrsZotS().getCnyVal(), oGrsZotP.getOGrsZotS().getSttVal());
                    oGrsZotP.getOGrsZotS().setSttNam(grsZntS.getSttNam());
                }
            }

            OGrsZotCPT oGrsZotCPT = new OGrsZotCPT();
            oGrsZotCPT.setOGrsZotPT(oGrsZotPT);

            return oGrsZotCPT;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZOT", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    @Override
    public OGrsZotS cnyPvc(final Integer cmpVal, final String usrVal, final String lngVal, final String cnyVal,
            final Integer pvcVal) {

        log.info("Tronweb business logic implementation cnyPvc have been called...");

        // reseting session
        resetSession();

        BigDecimal pmPvcVal = new BigDecimal(pvcVal);
        BigDecimal pmcmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {
            OGrsZotS lvOGrsZotS = cacheableAttribute.getZoneThree(cnyVal, pmPvcVal, lngVal, pmcmpVal);

            if (lvOGrsZotS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZotS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZOT", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    protected static OGrsZotP oGrsZotSToOGrsZotP(OGrsZotS oGrsZotS) {
        OGrsZotP oGrsZotP = new OGrsZotP();
        oGrsZotP.setOGrsZotS(oGrsZotS);
        oGrsZotP.setOTrnPrcS(new OTrnPrcS());

        return oGrsZotP;
    }

}
