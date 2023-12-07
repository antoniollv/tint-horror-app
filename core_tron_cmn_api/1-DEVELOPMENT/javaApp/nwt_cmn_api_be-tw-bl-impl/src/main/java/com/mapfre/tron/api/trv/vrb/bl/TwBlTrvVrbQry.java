package com.mapfre.tron.api.trv.vrb.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.trv.vrb.dl.ISrTrvVrbQryDAO;

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
public class TwBlTrvVrbQry extends TwBlCmnBase implements IBlTrvVrbQry {

    /** The cmnTyoQry repository. */
    @Autowired
    protected ISrTrvVrbQryDAO iSrTrvVrbQryDAO;

    /**
     * Query variable definition by company. It will return the variable definition
     * filtering by company
     * 
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param vrbNamVal -> Variable Name
     * @param vldDat    -> Validation Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
    @Override
    public List<OTrnVrbS> get(final String usrVal, final String lngVal, final Integer cmpVal, final String vrbNamVal,
            final Long vldDat) {

        log.info("Tronweb business logic implementation get variable definition by thrid party have been called...");

        try {
            List<OTrnVrbS> oPlyAtrSList = iSrTrvVrbQryDAO.get(usrVal, lngVal, cmpVal, vrbNamVal, vldDat);

            if (oPlyAtrSList == null || oPlyAtrSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oPlyAtrSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "VRB", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

    /**
     * Query variable definition by company. It will return the variable definition
     * filtering by company
     * 
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param vrbNamVal -> Variable Name
     * @param vldDat    -> Validation Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
    @Override
    public OTrnVrbS getById(final String usrVal, final String lngVal, final Integer cmpVal, final String vrbNamVal,
            final Long vldDat, final String vrbVal) {

        log.info("Tronweb business logic implementation getById variable definition by thrid party have been called...");

        try {
            OTrnVrbS oTrnVrbS = iSrTrvVrbQryDAO.getById(usrVal, lngVal, cmpVal, vrbNamVal, vldDat, vrbVal);

            if (oTrnVrbS == null) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oTrnVrbS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "VRB", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    /**
     * Query variable definition by line of business. It will return the variable
     * definition filtering by company and line of business
     * 
     * 
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param vrbNamVal -> Variable Name
     * @param vldDat    -> Validation Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
    @Override
    public List<OTrnVrbS> getVrb(final String usrVal, final String lngVal, final Integer cmpVal, final String vrbNamVal,
            final Long vldDat, final Integer lobVal) {

        log.info("Tronweb business logic implementation getVrb variable definition by thrid party have been called...");

        try {
            List<OTrnVrbS> oPlyAtrSList = iSrTrvVrbQryDAO.getVrb(usrVal, lngVal, cmpVal, vrbNamVal, vldDat, lobVal);

            if (oPlyAtrSList == null || oPlyAtrSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oPlyAtrSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "VRB", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

    /**
     * Variable Definition
     * 
     * 
     * @param usrVal       -> User code
     * @param lngVal       -> Language code
     * @param cmpVal       -> Company code
     * @param thpDcmTypVal -> Document type
     * @param thpDcmVal    -> Document
     * @param thpAcvVal    -> Activity
     * @param vrbNamVal    -> Variable Name
     * @param vldDat       -> Valid Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
    @Override
    public List<OTrnVrbS> getThp(final String usrVal, final String lngVal, final Integer cmpVal,
            final String thpDcmTypVal, final String thpDcmVal, final Integer thpAcvVal, final String vrbNamVal,
            final Long vldDat) {

        log.info("Tronweb business logic implementation getThp variable definition by thrid party have been called...");

        try {

            List<OTrnVrbS> oPlyAtrSList = iSrTrvVrbQryDAO.getVrb(usrVal, lngVal, cmpVal, thpDcmTypVal, thpDcmVal,
                    thpAcvVal, vrbNamVal, vldDat);

            if (oPlyAtrSList == null || oPlyAtrSList.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oPlyAtrSList;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "VRB", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

    /**
     * Update variable definition by thrid party. It will update the variable
     * definition by third party.
     *
     * @param usrVal               -> User code
     * @param lngVal               -> Language code
     * @param cmpVal               -> Company code
     * @param thpDcmTypVal         -> Document type
     * @param thpDcmVal            -> document
     * @param thpAcvVal            -> Activity
     * @param vrbNamVal            -> Variable Name
     * @param inVariableDefinition -> Input data to update the variable definition
     */
    @Override
    public void upd(final String usrVal, final String lngVal, final Integer cmpVal, final String thpDcmTypVal,
            final String thpDcmVal, final Integer thpAcvVal, final String vrbNamVal,
            final OTrnVrbS inVariableDefinition) {

        log.info("Tronweb business logic implementation Update variable definition by thrid party have been called...");

        // reseting session
        resetSession();

        iSrTrvVrbQryDAO.upd(usrVal, lngVal, cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal, vrbNamVal,
                inVariableDefinition);
    }

}
