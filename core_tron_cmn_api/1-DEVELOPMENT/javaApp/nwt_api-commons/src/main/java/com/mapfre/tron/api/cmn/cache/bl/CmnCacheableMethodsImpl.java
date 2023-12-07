package com.mapfre.tron.api.cmn.cache.bl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.mapfre.nwt.trn.cmn.mvd.bo.OCmnMvdS;
import com.mapfre.tron.api.cmn.mvd.dl.IDlCmnMvdDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * The own CMN cacheable methods implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 28 Mar 2023 - 11:41:26
 *
 */
@Slf4j
@Component
public class CmnCacheableMethodsImpl implements ICmnCacheableMethods {

    /** The Mvd repository.*/
    @Autowired
    protected IDlCmnMvdDAO iDlMvdDAO;

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
    @Cacheable("Poc-getMovementDefinitionCache")
    @Override
    public List<OCmnMvdS> getMovementDefinitionCache(final String lngVal, final String usrVal, final Integer cmpVal,
            final List<String> mvmIdn, final Long vldDat) {

        if (log.isInfoEnabled()) {
            log.info("getMovementDefinitionCache had been called.");
        }

        return iDlMvdDAO.getMovementDefinition(lngVal, usrVal, cmpVal, mvmIdn, vldDat);
    }

}
