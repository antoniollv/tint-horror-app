package com.mapfre.tron.api.crn.exr.bl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.trn.crn.exr.bo.OCrnExrP;
import com.mapfre.tron.api.bo.TronWebAccess;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.crn.exr.dl.ISrCrnExrQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb BlCrnExrQry business service implementation.
 *
 * @author pvraul1
 * @since 1.8
 * @version 23 abril. 2020 08:02:13
 *
 */
@Data
@Slf4j
@TronWebAccess
@EqualsAndHashCode(callSuper = false)
public class TwBlCrnExrQryImpl extends TwBlCmnBase implements IBlCrnExrQry {

    /** The CnrCnrQry repository. */
    @Autowired
    protected ISrCrnExrQryDAO iSrCnrExrQryDAO;

    @Override
    @Transactional("transactionManagerTwDl")
    public OCrnExrP chnDat(final String lngVal, final String usrVal, final Integer crnVal, final Long exrDat,
            final BigDecimal cmpVal) {

        log.info("Tronweb business logic implementation chnDat have been called...");

        // reseting session
        resetSession();

        try {
            return iSrCnrExrQryDAO.chnDat(lngVal,
                    usrVal,
                    BigDecimal.valueOf(crnVal),
                    new Date(exrDat),
                    cmpVal);

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "EXR", cmpVal);
        }

        return null;
    }

}
