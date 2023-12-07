package com.mapfre.tron.api.dsr.hdc.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.dsr.hdc.bo.ODsrHdcS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.dsr.hdc.dl.ISrDsrHdcQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Cristian Saball
 * @version 24/08/2021
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlDsrHdcQry extends TwBlCmnBase implements IBlDsrHdcQry {
	
    /** The DsrFdcQry repository.*/
    @Autowired
    protected ISrDsrHdcQryDAO iSrDsrHdcQryDAO;

    /**
     * Query Third Level Distribution Channel by company, first level and second
     * level.
     *
     * @author Cristian Saball
     *
     * @param cmpVal       -> company code
     * @param frsDstHnlVal -> First Channel Distribution
     * @param scnDstHnlVal -> Second Channel Distribution
     * @param usrVal       -> user value
     * @param lngVal       -> language value
     * 
     * @return List<ODsrHdcS>
     */
    @Override
    public List<ODsrHdcS> tblByCmpFrsScnLvl(final Integer cmpVal, final String frsDstHnlVal, final String scnDstHnlVal,
            final String usrVal, final String lngVal) {

        log.info("Tronweb business logic implementation TwBlDsrHdcQry.tblByCmpFrsScnLvl have been called...");

        // reseting session
        resetSession();

        BigDecimal pmCmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {
            List<ODsrHdcS> lvODsrHdcS = iSrDsrHdcQryDAO.tblByCmpFrsScnLvl(cmpVal, frsDstHnlVal, scnDstHnlVal, usrVal,
                    lngVal);

            if (lvODsrHdcS == null || lvODsrHdcS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvODsrHdcS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "HDC", pmCmpVal);
        }

        return Collections.emptyList();
    }

}
