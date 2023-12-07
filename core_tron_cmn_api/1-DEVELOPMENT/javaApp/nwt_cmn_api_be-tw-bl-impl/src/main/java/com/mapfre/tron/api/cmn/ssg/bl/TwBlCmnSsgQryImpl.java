package com.mapfre.tron.api.cmn.ssg.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmn.ssg.bo.OCmnSsgS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.ssg.dl.ISrCmnSsgQryDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmnSsgQryImpl extends TwBlCmnBase implements IBlCmnSsgQry {

    @Autowired
    ISrCmnSsgQryDAO iSrCmnSsgQryDAO;

    /**
     * Query Storage From Self-Service.
     *
     * @author Cristian Saball
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param sfrSciVal -> Self-Service Section
     * @param sfrSbsVal -> Self-Service Subsection
     * @param sfrIdnVal -> Authentication Identifier
     * @param vrbDspVal -> Variable Description
     * @return -> The variables saved for the self service front end
     */
    @Override
    public List<OCmnSsgS> getStorageFromSelfService(final Integer cmpVal, final String usrVal, final String lngVal,
            final String sfrSciVal, final String sfrSbsVal, final List<String> sfrIdnVal, final String vrbDspVal) {
        log.debug("Tronweb business logic implementation PRC have been called...");

        BigDecimal cmpValValue = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        // reseting session
        resetSession();

        try {
            List<OCmnSsgS> lOCmnSsgSList = iSrCmnSsgQryDAO.getStorageFromSelfService(cmpVal, usrVal, lngVal, sfrSciVal,
                    sfrSbsVal, sfrIdnVal, vrbDspVal);

            if (lOCmnSsgSList == null || lOCmnSsgSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lOCmnSsgSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "SSG", cmpValValue);
        }

        return Collections.emptyList();
    }

}
