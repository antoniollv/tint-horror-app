package com.mapfre.tron.api.cmu.thl.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.ins.c.CInsCmp;
import com.mapfre.nwt.ins.c.CInsConstant;
import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntP;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;
import com.mapfre.tron.api.cmn.adr.bl.IBlTranslatorThirdPartyModel;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmu.thl.dl.ISrCmuThlCntQryDAO;
import com.mapfre.tron.api.lvl.cnt.dl.ISrFrsLvlCntDAO;
import com.mapfre.tron.api.thp.adr.bl.IBlThpAdrQry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The tronweb BlCmuThlCntQry business service implementation.
 *
 * @author Cristian Saball
 * @since 1.8
 * @version 08 feb. 2021 14:43:13
 *
 */
@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmuThlCntQryImpl extends TwBlCmnBase implements IBlCmuThlCntQry {

    @Autowired
    protected ISrFrsLvlCntDAO iSrFrsLvlCntDAO;

    /** The CmuThlCntQry repository. */
    @Autowired
    protected ISrCmuThlCntQryDAO iSrCmuThlCntQryDAO;

    @Autowired
    protected IBlTranslatorThirdPartyModel iBlTranslatorThirdPartyModel;

    @Autowired
    protected IBlThpAdrQry iBlThpAdrQry;

    @Override
    public OThpCntP get(final Integer cmpVal, final String lngVal, final String usrVal, final Integer thrLvlVal) {

        log.info("Tronweb business logic implementation get have been called...");

        // reseting session
        resetSession();

        try {
            // TRON-8794
            if ("S".equalsIgnoreCase(CInsCmp.get(CInsConstant.MCA_MDT_THP_NEW, new BigDecimal(cmpVal)))) {
                List<OThpCntS> lvOThpCntST = iSrCmuThlCntQryDAO.getContactFromThirdLevelv1(cmpVal, lngVal, usrVal,
                        thrLvlVal);

                List<OThpCntS> pmOThpCntST = lvOThpCntST.stream()
                    .filter(oThpCntS -> "S".equalsIgnoreCase(oThpCntS.getDflCnh()))
                    .collect(Collectors.toList());

                List<OThpCntS> oldModelList = iBlTranslatorThirdPartyModel.getCntOldModelFromNewModel(pmOThpCntST,
                        usrVal, lngVal, new BigDecimal(cmpVal));

                if (oldModelList == null || oldModelList.isEmpty()) {
                    getErrorWithoutPrpIdn(lngVal, "CNT", BigDecimal.valueOf(cmpVal));
                } else {
                    OThpCntP lvOThpCntP = new OThpCntP();
                    lvOThpCntP.setOThpCntS(oldModelList.get(0));

                    return lvOThpCntP;
                }

            } else {
                return iSrCmuThlCntQryDAO.get(cmpVal, lngVal, usrVal, thrLvlVal);
            }

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "CNT", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

    /**
     * Query the third level contact information.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param thrLvlVal -> Third Level
     * @return -> It will return the contact of the third level of the commercial
     *         structure
     */
    @Override
    public List<OThpCntS> getContactFromThirdLevelv1(final Integer cmpVal, final String lngVal, final String usrVal,
            final Integer thrLvlVal) {

        if (log.isInfoEnabled()) {
            log.info("Tronweb business logic implementation getContactFromThirdLevelv1 have been called...");
        }

        // reseting session
        resetSession();

        try {
            if ("N".equalsIgnoreCase(CInsCmp.get(CInsConstant.MCA_MDT_THP_NEW, new BigDecimal(cmpVal)))) {
                OThpCntP lvOThpCntP = iSrCmuThlCntQryDAO.get(cmpVal, lngVal, usrVal, thrLvlVal);

                List<OThpCntS> lvOThpCntST = new ArrayList<>();
                lvOThpCntST.add(lvOThpCntP.getOThpCntS());

                List<OThpCntS> newModelList = iBlTranslatorThirdPartyModel.getCntNewModelFromOldModel(lvOThpCntST,
                        usrVal, lngVal, new BigDecimal(cmpVal));

                if (newModelList != null && !newModelList.isEmpty()) {
                    return newModelList;
                } else {
                    getErrorWithoutPrpIdn(lngVal, "CNT", new BigDecimal(cmpVal));
                }
            } else {
                return iSrCmuThlCntQryDAO.getContactFromThirdLevelv1(cmpVal, lngVal, usrVal, thrLvlVal);
            }

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "CNT", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

    /**
     * Query Address From Third Level 1.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param thrLvlVal -> Third Level
     * @return          -> It will return the address of the third level of the commercial structure.
     */
    @Override
    public List<OThpAdrS> getAddresstFromThirdLevelv1(final Integer cmpVal, final String lngVal, final String usrVal,
            final Integer thrLvlVal) {

        if (log.isInfoEnabled()) {
            log.info("Tronweb business logic implementation getAddresstFromThirdLevelv1 have been called...");
        }

        // reseting session
        resetSession();
        try {
            if ("N".equalsIgnoreCase(CInsCmp.get(CInsConstant.MCA_MDT_THP_NEW, new BigDecimal(cmpVal)))) {
                OThpAdrS lvOThpAdrS = iSrFrsLvlCntDAO.get(cmpVal, lngVal, usrVal, thrLvlVal);

                List<OThpAdrS> lvOThpAdrST = new ArrayList<>();
                lvOThpAdrST.add(lvOThpAdrS);

                List<OThpAdrS> newModelList = iBlTranslatorThirdPartyModel.getAdrNewModelFromOldModel(lvOThpAdrST,
                        usrVal, lngVal, new BigDecimal(cmpVal));

                if (newModelList != null && !newModelList.isEmpty()) {
                    return newModelList;
                } else {
                    getErrorWithoutPrpIdn(lngVal, "ADR", new BigDecimal(cmpVal));
                }

            } else {
        	List<OThpAdrS> lvOThpAdrS = iSrCmuThlCntQryDAO.getAddresstFromThirdLevelv1(cmpVal, lngVal, usrVal, thrLvlVal);
		for (OThpAdrS oThpAdrS : lvOThpAdrS) {
		    getOThpAdrSDescriptions(cmpVal, lngVal, usrVal, oThpAdrS);

		}

                return lvOThpAdrS;
            }

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ADR", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }
    
}
