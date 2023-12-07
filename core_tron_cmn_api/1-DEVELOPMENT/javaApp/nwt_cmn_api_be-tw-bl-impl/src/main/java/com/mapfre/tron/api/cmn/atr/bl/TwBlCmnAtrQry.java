package com.mapfre.tron.api.cmn.atr.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;
import com.mapfre.tron.api.cmn.atr.dl.ISrCmnAtrQryDAO;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.model.InVariableConceptsInformation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author AMINJOU
 * @version 24/02/2021
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmnAtrQry extends TwBlCmnBase implements IBLCmnAtrQry {

    /** The cmnTyoQry repository. */
    @Autowired
    protected ISrCmnAtrQryDAO iSrCmnAtrQryDAO;

    /**
     * 
     * @param usrVal                -> user value
     * @param lngVal                -> language value
     * @param inConstantInformation
     * @return List<OPlyAtrS>
     * 
     */
    @Override
    public List<OPlyAtrS> atr(final String usrVal, final String lngVal,
            final InVariableConceptsInformation inConstantInformation) {

        log.info("Tronweb business logic implementation TwBlCmnAtrQry.atr have been called...");

        // reseting session
        resetSession();

        try {
            List<OPlyAtrS> lvOPlyAtrS = iSrCmnAtrQryDAO.atr(usrVal, lngVal, inConstantInformation);

            if (lvOPlyAtrS == null || lvOPlyAtrS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOPlyAtrS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ATR", BigDecimal.valueOf(inConstantInformation.getCmpVal()));
        }

        return Collections.emptyList();
    }

}
