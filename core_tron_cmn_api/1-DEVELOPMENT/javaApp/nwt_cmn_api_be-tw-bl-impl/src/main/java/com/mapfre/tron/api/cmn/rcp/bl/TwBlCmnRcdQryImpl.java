
package com.mapfre.tron.api.cmn.rcp.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.rcd.tmd.bo.ORcdTmdS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.rcd.bl.IBLCmnRcdQry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The TronWeb BlCmnTypQry business logic service implementation.
 *
 * @author pvraul1
 * @since 1.8
 * @version 17 sept. 2019 9:00:11
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmnRcdQryImpl extends TwBlCmnBase implements IBLCmnRcdQry {

    /**
     * @author AMINJOU
     * 
     *         Query collector types v1. It will return the types of collector. It
     *         will be mandatory send the parameters defined in the service and the
     *         service will response with the output object defined.
     * 
     * @param usrVal
     * @param lngVal
     * @param cmpVal
     * 
     * @return List<ORcdTmdS>
     * 
     */
    @Override
    public List<ORcdTmdS> getCollectorTypesV1(final String usrVal, final String lngVal, final Integer cmpVal) {
        log.debug("Tronweb business logic implementation getCollectorTypesV1 have been called...");

        // reseting session
        resetSession();

        BigDecimal cmpValValue = (cmpVal != null) ? new BigDecimal(cmpVal) : cmpValDefault;

        try {
            List<ORcdTmdS> lvORcdTmdS = cacheableAttribute.getCollectorTypesV1(usrVal, lngVal, cmpValValue);

            if (lvORcdTmdS == null || lvORcdTmdS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            getCtmCssTypNam(lngVal, cmpValValue, lvORcdTmdS);

            return lvORcdTmdS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "RCD", cmpValValue);
        }

        return Collections.emptyList();
    }

    /**
     * @author AMINJOU
     * 
     *         Query collector type v1. It will return the type of collector. It
     *         will be mandatory send the parameters defined in the service and the
     *         service will response with the output object defined.
     * 
     * @param usrVal
     * @param lngVal
     * @param cmpVal
     * @param mnrTypVal
     * 
     * @return ORcdTmdS
     * 
     */
    @Override
    public ORcdTmdS getCollectorTypeV1(String usrVal, String lngVal, Integer cmpVal, String mnrTypVal) {
        log.debug("Tronweb business logic implementation getCollectorTypeV1 have been called...");

        // reseting session
        resetSession();

        BigDecimal cmpValValue = (cmpVal != null) ? new BigDecimal(cmpVal) : cmpValDefault;

        try {
            ORcdTmdS lvORcdTmdS = cacheableAttribute.getCollectorTypeV1(usrVal, lngVal, cmpValValue, mnrTypVal);

            if (lvORcdTmdS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            getCtmCssTypNam(lngVal, cmpValValue, lvORcdTmdS);

            return lvORcdTmdS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "RCD", cmpValValue);
        }

        return null;
    }

}
