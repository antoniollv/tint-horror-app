/**
 * 
 */
package com.mapfre.tron.api.cmn.typ.bl.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypCPT;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypP;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.typ.bl.IBLCmnTypQry;

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
public class TwBlCmnTypQryImpl extends TwBlCmnBase implements IBLCmnTypQry {

    @Override
    public OCmnTypCPT prc(final String fldNam, final BigDecimal lobVal, final String lngVal, final String getNamTypVal,
            final Integer cmpVal) {

        log.debug("Tronweb business logic implementation PRC have been called... ");

        // reseting session
        resetSession();

        BigDecimal cmpValValue = (cmpVal != null) ? new BigDecimal(cmpVal) : cmpValDefault;

        try {
            List<OCmnTypS> lOCmnTypS = cacheableAttribute.getOCmnTypS(fldNam, lobVal, lngVal, cmpValValue);

            if (lOCmnTypS == null || lOCmnTypS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            List<OCmnTypP> lOcmnTypPT = lOCmnTypS.stream().map(TwBlCmnTypQryImpl::getOCmnTypS)
                    .collect(Collectors.toList());

            OCmnTypCPT oCmnTypCPT = new OCmnTypCPT();
            oCmnTypCPT.setOCmnTypPT(lOcmnTypPT);

            return oCmnTypCPT;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "TYP", cmpValValue);
        }

        return null;
    }

    @Override
    public OCmnTypP fld(final String fldNam, final BigDecimal lobVal, final String lngVal, final String getNamTypVal,
            final String valVal, final Integer cmpVal) {

        log.info("Tronweb business logic implementation FLD have been called...");

        // reseting session
        resetSession();

        BigDecimal cmpValValue = new BigDecimal(cmpVal);
        try {
            OCmnTypS oCmnTypS = cacheableAttribute.getOCmnTypS(fldNam, valVal, lngVal, cmpValValue);

            if (oCmnTypS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            OCmnTypP oCmnTypP = new OCmnTypP();
            oCmnTypP.setOCmnTypS(oCmnTypS);

            return oCmnTypP;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "TYP", cmpValValue);
        }

        return null;
    }

    @Override
    public OCmnTypCPT Collect(final String lngVal, final Integer cmpVal) {
        log.debug("Tronweb business logic implementation Collect have been called...");

        // reseting session
        resetSession();

        BigDecimal cmpValValue = (cmpVal != null) ? new BigDecimal(cmpVal) : cmpValDefault;
        try {
            List<OCmnTypS> lOCmnTypS = cacheableAttribute.getOCmnTypSTypes(cmpValValue);

            if (lOCmnTypS == null || lOCmnTypS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            List<OCmnTypP> lOcmnTypPT = lOCmnTypS.stream().map(TwBlCmnTypQryImpl::getOCmnTypS)
                    .collect(Collectors.toList());

            OCmnTypCPT oCmnTypCPT = new OCmnTypCPT();
            oCmnTypCPT.setOCmnTypPT(lOcmnTypPT);

            return oCmnTypCPT;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "TMD", cmpValValue);
        }

        return null;
    }

    @Override
    @Transactional("transactionManagerTwDl")
    public OCmnTypS getCollectorType(final String usrVal, final String lngVal, final BigDecimal cmpVal,
            final String valVal) {

        log.debug("Tronweb business logic implementation getCollectorType have been called...");

        // reseting session
        resetSession();

        try {

            OCmnTypS oCmnTypS = cacheableAttribute.getCollectorType(usrVal, lngVal, cmpVal, valVal);

            if (oCmnTypS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oCmnTypS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "TMD", cmpVal);
        }

        return null;
    }

    @Override
    public OCmnTypS getType(final String usrVal, final String lngVal, final String fldNam, final String itcVal,
            final BigDecimal lobVal, final Integer cmpVal) {

        log.debug("Tronweb business logic implementation getType have been called...");

        // reseting session
        resetSession();

        BigDecimal cmpValValue = (cmpVal != null) ? new BigDecimal(cmpVal) : cmpValDefault;
        try {
            OCmnTypS oCmnTypS = cacheableAttribute.getOCmnTypS(fldNam, lobVal, lngVal, cmpValValue, itcVal);

            if (oCmnTypS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oCmnTypS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "TYP", cmpValValue);
        }

        return null;
    }

}
