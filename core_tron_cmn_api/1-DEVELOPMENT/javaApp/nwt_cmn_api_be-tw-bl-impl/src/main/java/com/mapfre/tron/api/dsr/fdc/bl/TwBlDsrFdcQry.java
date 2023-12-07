package com.mapfre.tron.api.dsr.fdc.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.dsr.fdc.bo.ODsrFdcS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.dsr.fdc.dl.ISrDsrFdcQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Javier Sangil
 * @version 12/08/2021
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlDsrFdcQry extends TwBlCmnBase implements IBlDsrFdcQry {

    /** The DsrFdcQry repository.*/
    @Autowired
    protected ISrDsrFdcQryDAO iSrDsrFdcQryDAO;

    /**
     * Query First Level Distribution Channel by company
     *
     * @author Javier S.
     * 
     * @purpose Query First Level Distribution Channel by company
     *          
     * @param cmpVal -> company code
     * @param usrVal -> user value
     * @param lngVal -> language value
     * 
     * @return List<ODsrFdcS>
     * 
     */
    @Override
    public List<ODsrFdcS> tblByCmp(final Integer cmpVal, final String usrVal, final String lngVal) {
        log.info("Tronweb business logic implementation TwBlDsrFdcQry.tblByCmp have been called...");
        // reseting session
        resetSession();

        BigDecimal pmCmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {
            List<ODsrFdcS> lvODsrFdcS = iSrDsrFdcQryDAO.tblByCmp(cmpVal, usrVal, lngVal);

            if (lvODsrFdcS == null || lvODsrFdcS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvODsrFdcS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getError(lngVal, pmCmpVal);
        }

        return Collections.emptyList();
    }

    /**
     * First Level Distribution Channel by Company.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language value
     * @param usrVal       -> User value
     * @param frsDstHnlVal -> First level distribution channel
     * @return             -> The first level distribution channel by company
     */
    @Override
    public ODsrFdcS tblByCmpById(final Integer cmpVal, final String lngVal, final String usrVal,
            final String frsDstHnlVal) {

        log.info("Tronweb business logic implementation TwBlDsrFdcQry.tblByCmpById have been called...");

        // reseting session
        resetSession();

        try {
            return iSrDsrFdcQryDAO.tblByCmpById(cmpVal, usrVal, lngVal, frsDstHnlVal);

        } catch (IncorrectResultSizeDataAccessException e) {
            getError(lngVal, new BigDecimal(cmpVal));
        }

        return null;
    }

}
