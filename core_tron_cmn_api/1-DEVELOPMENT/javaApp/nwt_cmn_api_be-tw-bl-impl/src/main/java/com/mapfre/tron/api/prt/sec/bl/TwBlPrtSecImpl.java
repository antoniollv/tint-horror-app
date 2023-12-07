package com.mapfre.tron.api.prt.sec.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;
import com.mapfre.nwt.trn.prt.sec.bo.OPrtSecS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * TwBlPrtSecImpl
 *
 * @author aminjou
 * @since 1.8
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper=false)
public class TwBlPrtSecImpl extends TwBlCmnBase implements IBlPrtSec {

    /**
     * Query sectors by company. It will return the types of languages. It will be
     * mandatory send the parameters defined in the service and the service will
     * response with the output object defined. operationId: getsectorList
     * 
     * @param cmpVal
     * @param lngVal
     * @param usrVal
     * 
     * @return List<OPrtSecS>
     */
    @Override
    public List<OPrtSecS> getSec(final Integer cmpVal, final String lngVal, final String usrVal) {
        log.info("Tronweb business logic implementation getSec have been called...");

        // reseting session
        resetSession();

        try {
            List<OPrtSecS> lvOPrtSecS = cacheableAttribute.getSecList(new BigDecimal(cmpVal), lngVal, usrVal);

            if (lvOPrtSecS == null || lvOPrtSecS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOPrtSecS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "SEC", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

    /**
     * Query currency. It will return the currency.
     *
     * @author Cristian Saball
     *
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> company
     * @param sdrCrnVal -> standard currency value
     * @return OCrnCrnS -> the currency
     */
    @Override
    public OPrtSecS getSecQry(final Integer cmpVal, final String lngVal, final String usrVal, final String secVal) {
        log.info("Tronweb business logic implementation getSecQry have been called...");

        // reseting session
        resetSession();

        BigDecimal pmCmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;
        BigDecimal pmsecVal = (secVal != null) ? new BigDecimal(secVal) : null;

        try {
            OPrtSecS lvOPrtSecS = cacheableAttribute.getSector(pmCmpVal, lngVal, usrVal, pmsecVal);

            if (lvOPrtSecS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOPrtSecS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "SEC", pmCmpVal);
        }

        return null;
    }

    /**
     * Query sector. It will return the sector
     *
     * @author Amin Joudi
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cmpVal -> Company code
     * @param secVal -> Sector
     * @param sbsVal -> SubSector value
     * @return OPrtSecS -> the sector
     */
    @Override
    public OPrtSbsS getSubSector(final Integer cmpVal, final Integer secVal, final Integer sbsVal, final String lngVal,
            final String usrVal) {

        log.info("Tronweb business logic implementation getSubSector have been called...");

        // reseting session
        resetSession();

        BigDecimal pmCmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;
        BigDecimal pmsecVal = (secVal != null) ? new BigDecimal(secVal) : null;
        BigDecimal pmsbsVal = (sbsVal != null) ? new BigDecimal(sbsVal) : null;

        try {
            OPrtSbsS lvOPrtSecS = cacheableAttribute.getSubSector(pmCmpVal, pmsecVal, pmsbsVal, lngVal, usrVal);

            if (lvOPrtSecS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOPrtSecS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "SBS", pmCmpVal);
        }

        return null;
    }

}
