package com.mapfre.tron.api.cmn.mvd.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.mvd.bo.OCmnMvdS;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.cache.bl.ICmnCacheableMethods;

import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb CmnMvd query business service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Mar 2023 - 17:30:30
 *
 */
@Slf4j
@Service
public class TwBlCmnMvdQryImpl extends TwBlCmnBase implements IBlCmnMvdQry {

    /** The own CMN cacheable methods component.*/
    @Autowired
    protected ICmnCacheableMethods iCmnCacheableMethods;

    /**
     * Query movement definition.
     *
     * @param lngVal -> Language code
     * @param usrVal -> User code
     * @param cmpVal -> Company code
     * @param mvmIdn -> Movement Identification
     * @param vldDat -> Valid date
     * @return       -> It will return the defintion of movements.
     */
    @Override
    public List<OCmnMvdS> getMovementDefinition(final String lngVal, final String usrVal, final Integer cmpVal,
            final List<String> mvmIdn, final Long vldDat) {

        if (log.isInfoEnabled()) {
            log.info("The tronweb getMovementDefinition service had been called.");
        }

        // reseting session
        resetSession();

        try {
            List<OCmnMvdS> lvOCmnMvdST = iCmnCacheableMethods.getMovementDefinitionCache(lngVal, usrVal, cmpVal, mvmIdn,
                    vldDat);

            if (lvOCmnMvdST == null || lvOCmnMvdST.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            lvOCmnMvdST.parallelStream().forEach(oCmnMvdS -> {
                try {
                    OCmnTypS lvOCmnTypS = cacheableAttribute.getOCmnTypS("NTE_CLF_VAL",
                            oCmnMvdS.getNteClfVal(),
                            lngVal,
                            new BigDecimal(cmpVal));
                    oCmnMvdS.setNteClfNam(lvOCmnTypS.getItcNam());
                } catch (Exception e) {
                    log.error(String.format("Error getting NteClf[%s] Name: %s", oCmnMvdS.getNteClfVal(), e.getMessage()));
                }
            });

            return lvOCmnMvdST;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "MVD", new BigDecimal(cmpVal));
        }

        return Collections.emptyList();
    }

}
