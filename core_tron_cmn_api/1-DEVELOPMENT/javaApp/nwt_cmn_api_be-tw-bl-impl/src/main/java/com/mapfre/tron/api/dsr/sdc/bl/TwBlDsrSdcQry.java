package com.mapfre.tron.api.dsr.sdc.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.dsr.sdc.bo.ODsrSdcS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.dsr.sdc.dl.ISrDsrSdcQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Cristian Saball
 * @version 19/08/2021
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlDsrSdcQry extends TwBlCmnBase implements IBlDsrSdcQry {

    /** The DsrFdcQry repository.*/
    @Autowired
    protected ISrDsrSdcQryDAO iSrDsrSdcQryDAO;

    /**
     * Second Level Distribution Channel by company and first level
     *
     * @author Cristian S.
     *
     * @param cmpVal       -> company code
     * @param frsDstHnlVal -> first Channel Distribution
     * @param usrVal       -> user value
     * @param lngVal       -> language value
     *
     * @return List<ODsrSdcS>
     */
    @Override
    public List<ODsrSdcS> tblByCmpFrsLvl(final Integer cmpVal, final String frsDstHnlVal, final String usrVal,
            final String lngVal) {

        log.info("Tronweb business logic implementation TwBlDsrSdcQry.tblByCmpFrsLvl have been called...");

        // reseting session
        resetSession();

        BigDecimal pmCmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {
            List<ODsrSdcS> lvODsrSdcSList = iSrDsrSdcQryDAO.tblByCmpFrsLvl(cmpVal, frsDstHnlVal, usrVal, lngVal);

            if (lvODsrSdcSList == null || lvODsrSdcSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvODsrSdcSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getError(lngVal, pmCmpVal);
        }

        return Collections.emptyList();
    }

}
