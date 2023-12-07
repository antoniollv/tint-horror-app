package com.mapfre.tron.api.ard.aby.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb CmnBL business service implementation.
 *
 * @author ARTEAG3
 * @since 1.8
 * @version 26 jun. 2020
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlArdAbvQryImpl extends TwBlCmnBase implements IBlSrArdAbvQry {

    /**
     * Query Attribute Values
     * 
     * @author ARTEAG3
     * @param lngVal -> Language code
     * @param usrVal -> User
     * @param cmpVal -> Company
     * @param fldNam -> Field code
     * @param lobVal -> Line of Business
     * @param mdtVal -> Modality
     * @return -> List<OArdAbvS>
     */
    @Override
    public List<OArdAbvS> tbl(final String lngVal, final String usrVal, final Integer cmpVal, final String fldNam,
            final Integer lobVal, final Integer mdtVal) {

        log.info("Tronweb business logic implementation tbl have been called...");

        // reseting session
        resetSession();

        BigDecimal cmpValBigDec = null;
        if (cmpVal != null) {
            cmpValBigDec = new BigDecimal(cmpVal);
        }
        BigDecimal lobValBigDec = null;
        if (lobVal != null) {
            lobValBigDec = new BigDecimal(lobVal);
        }
        BigDecimal mdtValBigDec = null;
        if (mdtVal != null) {
            mdtValBigDec = new BigDecimal(mdtVal);
        }

        try {
            List<OArdAbvS> oArdAbvSList = cacheableAttribute.getAttributeValues(cmpValBigDec, lobValBigDec,
                    mdtValBigDec, fldNam, lngVal);

            if (oArdAbvSList == null || oArdAbvSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oArdAbvSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ABV", cmpValBigDec);
        }

        return Collections.emptyList();
    }

}
