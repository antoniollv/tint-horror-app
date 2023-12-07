package com.mapfre.tron.api.lvl.cnt.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmu.snl.bo.OCmuSnlS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.lvl.cnt.dl.ISrScnLvlCntDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * TwBlScnLvlCntImpl
 *
 * @author magarafr
 * @since 1.8
 * @version 09 feb. 2021 12:54:02
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlScnLvlCntImpl extends TwBlCmnBase implements IBlScnLvlCnt {

    @Autowired
    protected ISrScnLvlCntDAO iSrScnLvlCntDAO;

    /**
     * Query Contact From Second Level
     *
     * @author magarafr
     * @purpose Query the second level contact information. It will return the
     *          contact of the second level of the commercial structure . It will be
     *          mandatory send the parameters defined in the service and the service
     *          will response with the output object defined.
     *
     * @param cmpVal    -> Company code
     * @param scnLvlVal -> Second Level
     * @param lngVal	-> Language code
     * @return          -> OThpCntS
     */
    @Override
    public OThpCntS scnLvlCnt(final Integer cmpVal,final Integer scnLvlVal,final String lngVal) {

        log.info("Tronweb business logic implementation scnLvlCnt have been called...");

        // reseting session
        resetSession();

        try {
            OThpCntS lvOThpCntS = iSrScnLvlCntDAO.scnLvlCnt(cmpVal, scnLvlVal);

            if (lvOThpCntS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOThpCntS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "CNT", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    /**
     * Query Second Level List.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param frsLvlVal -> First Level
     * @return          -> The second levels list
     */
    @Override
    public List<OCmuSnlS> scnLvlQry(final Integer cmpVal, final String lngVal, final String usrVal,
            final BigDecimal frsLvlVal) {

        log.info("Tronweb business logic implementation scnLvlQry have been called...");

        // reseting session
        resetSession();

        try {
            List<OCmuSnlS> lvOCmuSnlST = cacheableAttribute.getSecondLevels(cmpVal, lngVal, usrVal, frsLvlVal);

            if (lvOCmuSnlST == null || lvOCmuSnlST.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCmuSnlST;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "SNL", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

}
