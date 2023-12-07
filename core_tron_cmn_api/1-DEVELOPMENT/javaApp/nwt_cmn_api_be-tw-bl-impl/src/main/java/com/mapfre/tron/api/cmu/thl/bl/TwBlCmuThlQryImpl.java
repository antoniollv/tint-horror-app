package com.mapfre.tron.api.cmu.thl.bl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlP;
import com.mapfre.tron.api.bo.TronWebAccess;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmu.thl.dl.ISrCmuThlQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb BlCmuThlQry business service implementation.
 *
 * @author USOZALO
 * @since 1.8
 * @version 04 jun. 2020 14:43:13
 *
 */
@Data
@Slf4j
@TronWebAccess
@EqualsAndHashCode(callSuper = false)
public class TwBlCmuThlQryImpl extends TwBlCmnBase implements IBlCmuThlQry{

    /** The CnrCnrQry repository.*/
    @Autowired
    protected ISrCmuThlQryDAO iSrCmuThlQryDAO;

    @Override
    @Transactional("transactionManagerTwDl")
    public OCmuThlP get(final Integer cmpVal, final String lngVal, final String usrVal, final Integer thrLvlVal) {

        log.info("Tronweb business logic implementation get have been called...");

        // reseting session
        resetSession();

        BigDecimal pmCmpVal = (cmpVal != null) ? new BigDecimal(cmpVal) : null;
        BigDecimal pmThrLvlVal = (thrLvlVal != null) ? new BigDecimal(thrLvlVal) : null;

        try {
            return iSrCmuThlQryDAO.get(pmCmpVal, lngVal, usrVal, pmThrLvlVal);

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "THL", pmCmpVal);
        }

        return null;
    }

}
