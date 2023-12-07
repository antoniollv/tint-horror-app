
package com.mapfre.tron.api.prt.lob.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.pid.c.CPid;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobCPT;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobP;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb CmnBL business service implementation.
 *
 * @author pvraul1
 * @since 1.8
 * @version 6 ago. 2019 12:41:13
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlPrtLobQryImpl extends TwBlCmnBase implements IBlPrtLobQry {

    @Autowired
    protected ISrPrtLobQryDAO iSrPrtLobQryDAO;

    @Override
    @Transactional("transactionManagerTwDl")
    public OPrtLobCPT isuTbl(final Integer cmpVal, final String usrVal, final String lngVal) {

        log.info("Tronweb business logic implementation isuTbl have been called...");

        // reseting session
        resetSession();

        try {
            List<OPrtLobS> oPrtLobS = cacheableAttribute.getAllForLineOfBusiness(BigDecimal.valueOf(cmpVal), lngVal);

            if (oPrtLobS == null || oPrtLobS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            List<OPrtLobP> oPrtLobPList = new ArrayList<>();

            for (OPrtLobS oPrtLobS2 : oPrtLobS) {
                if (oPrtLobS2 != null) {
                    getSecNam(cmpVal, oPrtLobS2);
                }

                if (BigDecimal.valueOf(oPrtLobS.size()).compareTo(CTrn.ZRO) > 0) {
                    this.isuTblAux(oPrtLobS2);
                }

                OPrtLobP oPrtLobP = new OPrtLobP();
                oPrtLobP.setOPrtLobS(oPrtLobS2);
                oPrtLobPList.add(oPrtLobP);
            }

            OPrtLobCPT oPrtLobCPT = new OPrtLobCPT();
            oPrtLobCPT.setOPrtLobPT(oPrtLobPList);

            return oPrtLobCPT;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "LOB", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    @Override
    @Transactional("transactionManagerTwDl")
    public OPrtLobP get(final String lngVal, final Integer cmpVal, final String usrVal, final Integer lobVal,
            final Long vldDat) {

        log.info("Tronweb business logic implementation get have been called...");

        // reseting session
        resetSession();

        BigDecimal lobValBd = (lobVal != null) ? new BigDecimal(lobVal) : null;
        BigDecimal cmpValBd = (cmpVal != null) ? new BigDecimal(cmpVal) : null;

        try {
            List<OPrtLobS> lvOPrtLobS = cacheableAttribute.getOPrtLobS(cmpValBd, lobValBd, new Date(vldDat), lngVal);

            if (lvOPrtLobS == null || lvOPrtLobS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            OPrtLobP lvOPrtLobP = new OPrtLobP();
            lvOPrtLobP.setOPrtLobS(lvOPrtLobS.get(0));

            return lvOPrtLobP;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "LOB", cmpValBd);
        }

        return null;
    }

    protected void isuTblAux(OPrtLobS oPrtLobS2) {
        if (CPid.POC_VAL_MOT.equals(oPrtLobS2.getPocVal())) {
            oPrtLobS2.setExsAcr(CIns.YES);
            oPrtLobS2.setPorCmsEqlNewPdc(CIns.NO);
        } else {
            oPrtLobS2.setExsAcr(CIns.NO);
            oPrtLobS2.setPorCmsEqlNewPdc(CIns.YES);
        }
        if (CPid.POC_VAL_TNR.equals(oPrtLobS2.getPocVal())) {
            oPrtLobS2.setExsApl(CIns.YES);
        } else {
            oPrtLobS2.setExsApl(CIns.NO);
        }
        if (CPid.POC_VAL_LIV.equals(oPrtLobS2.getPocVal())) {
            oPrtLobS2.setBlbMdt(CIns.YES);
            oPrtLobS2.setCmcAty(CIns.YES);
        } else {
            oPrtLobS2.setBlbMdt(CIns.NO);
            oPrtLobS2.setCmcAty(CIns.NO);
        }
    }

    /**
     * Query line of business by deals.
     *
     * @author Cristian Saball
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param delVal -> Deal value
     * @param vldDat -> Validation Date
     *
     * @return -> The list of line of business by deals
     */
    @Override
    public List<OPrtLobS> getLineOfBusinessByDeal(final String lngVal, final Integer cmpVal, final String usrVal,
            final String delVal, final Long vldDat) {

        BigDecimal cmpValBd = (cmpVal != null) ? new BigDecimal(cmpVal) : null;
        try {
            List<OPrtLobS> oPrtLobST = iSrPrtLobQryDAO.getLineOfBusinessByDeal(lngVal, cmpVal, usrVal, delVal, vldDat);

            if (oPrtLobST == null || oPrtLobST.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return oPrtLobST;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "LOB", cmpValBd);
        }

        return Collections.emptyList();
    }

}
