package com.mapfre.tron.api.grs.zon.bl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.grs.zon.dl.ISrGrsZonSbdQryDAO;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb GrsZonSbdQry service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 31 Mar 2022 - 12:36:26
 *
 */
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlGrsZonSbdQryImpl extends TwBlCmnBase implements IBlGrsZonSbdQry {

    /** The GrsZonSbdQry repository. */
    @Autowired
    protected ISrGrsZonSbdQryDAO iSrGrsZonSbdQryDAO;

    /**
     * Query Zone Four Subcode.
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country
     * @param sttVal    -> State Value
     * @param pvcVal    -> Province Value
     * @param twnVal    -> Town Value
     * @param ditVal    -> District Value
     * @param reaGrsDit -> Real District
     * @return          -> It will return the zone four subcode (District)
     */
    @Override
    public OGrsZnfS getZoneFourSubcode(final Integer cmpVal, final String usrVal, final String lngVal,
            final String cnyVal, final Integer sttVal, final Integer pvcVal, final Integer twnVal, final String ditVal,
            final String reaGrsDit) {

        log.info("Tronweb business logic implementation getZoneFourSubcode have been called...");

        // reseting session
        resetSession();

        OGrsZnfS lvOGrsZnfS = null;
        try {
            lvOGrsZnfS = iSrGrsZonSbdQryDAO.getZoneFourSubcode(cmpVal, usrVal, lngVal, cnyVal, sttVal, pvcVal, twnVal,
                    ditVal, reaGrsDit);

            setCnyNam(cmpVal, usrVal, lngVal, lvOGrsZnfS);
            setSttNam(cmpVal, usrVal, lngVal, lvOGrsZnfS);
            setPvcNam(cmpVal, lngVal, lvOGrsZnfS);
            setTwnNam(cmpVal, usrVal, lngVal, lvOGrsZnfS);

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZOF", new BigDecimal(cmpVal));
        }

        return lvOGrsZnfS;
    }

    /**
     * Query Zone Four Subcode List.
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country
     * @param sttVal    -> State Value
     * @param pvcVal    -> Province Value
     * @param twnVal    -> Town Value
     * @param reaGrsDit -> Real District
     * @return          -> It will return the list of zone four subcode list (Districts)
     */
    @Override
    public List<OGrsZnfS> getZoneFourSubcodeList(final Integer cmpVal, final String usrVal, final String lngVal,
            final String cnyVal, final String sttVal, final Integer pvcVal, final Integer twnVal,
            final String reaGrsDit) {

        log.info("Tronweb business logic implementation getZoneFourSubcodeList have been called...");

        // reseting session
        resetSession();

        List<OGrsZnfS> lvOGrsZnfST = null;

        try {
            lvOGrsZnfST = iSrGrsZonSbdQryDAO.getZoneFourSubcodeList(cmpVal, usrVal, lngVal, cnyVal, sttVal, pvcVal,
                    twnVal, reaGrsDit);

            if (lvOGrsZnfST != null && !lvOGrsZnfST.isEmpty()) {
                for (OGrsZnfS lvOGrsZnfS : lvOGrsZnfST) {
                    setCnyNam(cmpVal, usrVal, lngVal, lvOGrsZnfS);
                    setSttNam(cmpVal, usrVal, lngVal, lvOGrsZnfS);
                    setPvcNam(cmpVal, lngVal, lvOGrsZnfS);
                    setTwnNam(cmpVal, usrVal, lngVal, lvOGrsZnfS);
                }
            } else {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ZOF", new BigDecimal(cmpVal));
        }

        return lvOGrsZnfST;
    }

}
