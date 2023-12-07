package com.mapfre.tron.api.cmn.mvr.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.mvr.dl.ISrCmnMvsQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Javier Sangil
 *
 */

@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmnMvrQryImpl extends TwBlCmnBase implements IBlCmnMvrQry {

    /** The CmnMvsQry service. */
    @Autowired
    private ISrCmnMvsQryDAO iSrCmnMvsQryDAO;

    /**
     * Query movements record by third party
     * 
     * @author Javier Sangil
     * 
     * @param lngVal       -> Language code
     * @param usrVal       -> User code
     * @param cmpVal       -> Company code
     * @param thpDcmTypVal -> Document type
     * @param thpDcmVal    -> document
     * @param thpAcvVal    -> Activity
     * @param mvmIdn       -> Third Channel Distribution
     * @param qryDat       -> Initial Date
     * @param mvmSttTypVal -> Movement state type
     * @param mvmPbl       -> Public Movement
     * @param stsTypVal    -> Status Type Value
     * @param prcTypVal    -> Process Type value
     * @return -> List<OCmnMvrS>
     */
    @Override
    public List<OCmnMvrS> dcmTbl(final String lngVal, final String usrVal, final Integer cmpVal,
            final String thpDcmTypVal, final String thpDcmVal, final Integer thpAcvVal, final List<String> mvmIdn,
            final Long qryDat, final String mvmSttTypVal, final String mvmPbl, final List<String> stsTypVal,
            final String prcTypVal) {

        log.info("Tronweb dcmTbl implementation service has been called...");

        // reseting session
        resetSession();

        try {
            List<OCmnMvrS> oCmnMvrS = iSrCmnMvsQryDAO.dcmTbl(lngVal, usrVal, cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal,
                    mvmIdn, qryDat, mvmSttTypVal, mvmPbl, stsTypVal, prcTypVal, false);

            if (oCmnMvrS == null || oCmnMvrS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oCmnMvrS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "MVR", new BigDecimal(cmpVal));
        }

        return Collections.emptyList();
    }

}
