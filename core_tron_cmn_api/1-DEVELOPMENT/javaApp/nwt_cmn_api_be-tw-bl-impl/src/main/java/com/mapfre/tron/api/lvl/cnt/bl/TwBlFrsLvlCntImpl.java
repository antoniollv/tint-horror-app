package com.mapfre.tron.api.lvl.cnt.bl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.lvl.cnt.dl.ISrFrsLvlCntDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * TwBlFrsLvlCntImpl
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
public class TwBlFrsLvlCntImpl extends TwBlCmnBase implements IBlFrsLvlCnt {

    @Autowired
    protected ISrFrsLvlCntDAO iSrFrsLvlCntDAO;

    /**
     * Query Contact From First Level
     *
     * @author magarafr
     * @purpose Query the first level contact information. It will return the
     *          contact of the first level of the commercial structure . It will be
     *          mandatory send the parameters defined in the service and the service
     *          will response with the output object defined.
     *
     * @param cmpVal    -> Company code
     * @param frsLvlVal -> First Level
     * @return          -> OThpCntS
     */
    @Override
    public OThpCntS frsLvlCnt(final Integer cmpVal, final Integer frsLvlVal, final String lngVal) {

        log.info("Tronweb business logic implementation frsLvlCnt have been called...");

        // reseting session
        resetSession();

        try {
            return iSrFrsLvlCntDAO.frsLvlCnt(cmpVal, frsLvlVal);

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "CNT", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

}
