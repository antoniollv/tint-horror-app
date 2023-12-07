package com.mapfre.tron.api.grs.zof.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;
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
public class TwBlSrGrsZofQryImpl extends TwBlCmnBase implements IBlGrsZofQry {

    @Override
    @Transactional("transactionManagerTwDl")
    public List<OGrsZofS> tbl(final Integer cmpVal, final String usrVal, final String lngVal, final String cnyVal,
            final Integer sttVal, final Integer pvcVal, final Integer twnVal, final String reaPsc) {

        log.info("Tronweb business logic implementation tbl have been called...");

        // reseting session
        resetSession();

        BigDecimal pmSttVal = (sttVal != null) ? new BigDecimal(sttVal) : null;
        BigDecimal pmPvcVal = (pvcVal != null) ? new BigDecimal(pvcVal) : null;
        BigDecimal pmTwnVal = (twnVal != null) ? new BigDecimal(twnVal) : null;
        BigDecimal pmcmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {

            List<OGrsZofS> lvOGrsZofSList = cacheableAttribute.getAllZoneFive(pmcmpVal, usrVal, lngVal, cnyVal,
                    pmSttVal, pmPvcVal, pmTwnVal, reaPsc.toUpperCase());

            if (lvOGrsZofSList == null || lvOGrsZofSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZofSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZOF", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

    /**
     * Query zone five list. It will return the list of zone five (postalCode). It
     * will be mandatory send the parameters defined in the service and the service
     * will response with the output object defined.
     *
     * @param usrVal
     * @param lngVal
     * @param pslCodVal
     * @param reaPsc
     *
     * @return List<OGrsZofS>
     */
    @Override
    public List<OGrsZofS> get(final String pslCodVal, final String usrVal, final String lngVal, final String reaPsc) {

        log.info("Tronweb business logic implementation get have been called...");

        // reseting session
        resetSession();

        try {
            List<OGrsZofS> lvOGrsZofSList = cacheableAttribute.getZoneFiveByPslCodVal(pslCodVal, usrVal, lngVal, reaPsc,
                    cmpValDefault);

            if (lvOGrsZofSList == null || lvOGrsZofSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOGrsZofSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZOF", cmpValDefault);
        }

        return Collections.emptyList();
    }

}
