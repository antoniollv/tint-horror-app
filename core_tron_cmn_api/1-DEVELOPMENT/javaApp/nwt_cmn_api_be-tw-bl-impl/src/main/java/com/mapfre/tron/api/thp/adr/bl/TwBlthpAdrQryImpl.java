package com.mapfre.tron.api.thp.adr.bl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.ins.c.CInsCmp;
import com.mapfre.nwt.ins.c.CInsConstant;
import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;
import com.mapfre.tron.api.cmn.adr.bl.IBlTranslatorThirdPartyModel;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmu.thl.dl.ISrCmuThlCntQryDAO;
import com.mapfre.tron.api.lvl.cnt.dl.ISrFrsLvlCntDAO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlthpAdrQryImpl extends TwBlCmnBase implements IBlThpAdrQry {

    @Autowired
    protected ISrFrsLvlCntDAO iSrFrsLvlCntDAO;

    @Autowired
    protected IBlTranslatorThirdPartyModel iBlTranslatorThirdPartyModel;

    @Autowired
    protected ISrCmuThlCntQryDAO iSrCmuThlCntQryDAO;

    @Override
    public OThpAdrS get(final Integer cmpVal, final String lngVal, final String usrVal, final Integer thrLvlVal) {
        log.info("Tronweb business logic implementation get have been called...");

        // reseting session
        resetSession();

        try {
            // TRON-8795
            if ("S".equalsIgnoreCase(CInsCmp.get(CInsConstant.MCA_MDT_THP_NEW, new BigDecimal(cmpVal)))) {
                List<OThpAdrS> lvOThpAdrST = iSrCmuThlCntQryDAO.getAddresstFromThirdLevelv1(cmpVal, lngVal, usrVal,
                        thrLvlVal);

                List<OThpAdrS> pmThpAdrST = lvOThpAdrST.stream()
                        .filter(oThpAdrS -> "S".equalsIgnoreCase(oThpAdrS.getDflAdr()))
                        .collect(Collectors.toList());

                List<OThpAdrS> oldModelList = iBlTranslatorThirdPartyModel.getAdrOldModelFromNewModel(
                        pmThpAdrST,
                        usrVal,
                        lngVal,
                        new BigDecimal(cmpVal));

                if (oldModelList == null || oldModelList.isEmpty()) {
                    getErrorWithoutPrpIdn(lngVal, "ADR", BigDecimal.valueOf(cmpVal));
                } else {
                    OThpAdrS oThpAdrS = oldModelList.get(0);
                    
                    getOThpAdrSDescriptions(cmpVal, lngVal, usrVal, oThpAdrS);
                    
                    return oThpAdrS;
                }

            } else {
                return iSrFrsLvlCntDAO.get(cmpVal, lngVal, usrVal, thrLvlVal);
            }

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "ADR", BigDecimal.valueOf(cmpVal));
        }

        return null;
    }

}
