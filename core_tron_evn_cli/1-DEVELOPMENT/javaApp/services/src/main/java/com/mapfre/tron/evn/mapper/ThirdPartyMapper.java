package com.mapfre.tron.evn.mapper;

import com.mapfre.nwt.trn.thp.acv.bo.OThpAcvS;
import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrP;
import com.mapfre.nwt.trn.thp.arm.bo.OThpArmP;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntP;
import com.mapfre.nwt.trn.thp.ipc.bo.OThpIpcC;
import com.mapfre.nwt.trn.thp.lcn.bo.OThpLcnP;
import com.mapfre.nwt.trn.thp.lgr.bo.OThpLgrP;
import com.mapfre.nwt.trn.thp.pce.bo.OThpPceP;
import com.mapfre.nwt.trn.thp.pcm.bo.OThpPcmP;
import com.mapfre.nwt.trn.thp.shl.bo.OThpShlP;
import com.mapfre.nwt.trn.thp.tmn.bo.OThpTmnP;
import com.mapfre.nwt.trn.thp.txl.bo.OThpTxlP;
import com.mapfre.nwt.trn.thp.uni.bo.OThpUniP;
import com.mapfre.nwt.trn.tpd.alp.bo.OTpdAlpP;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class ThirdPartyMapper {

    public void completeData(OThpIpcC oThpIpcC, Date mdfDat, Date pocDat){
        OThpAcvS thirdPartyActivity = oThpIpcC.getOThpAcvP().getOThpAcvS();
        thirdPartyActivity.setMdfDat(mdfDat);
        thirdPartyActivity.setPocDat(pocDat);
    }

    public void clearEmptyLists(OThpIpcC oThpIpcC) {
        oThpIpcC.setOThpAdrPT((ArrayList<OThpAdrP>) oThpIpcC.getOThpAdrPT().stream().filter(o -> !isEmpty(o.getOThpAdrS())).collect(Collectors.toList()));
        oThpIpcC.setOTpdAlpPT((ArrayList<OTpdAlpP>) oThpIpcC.getOTpdAlpPT().stream().filter(o -> !isEmpty(o.getOTpdAlpS())).collect(Collectors.toList()));
        oThpIpcC.setOThpArmPT((ArrayList<OThpArmP>) oThpIpcC.getOThpArmPT().stream().filter(o -> !isEmpty(o.getOThpArmS())).collect(Collectors.toList()));
        oThpIpcC.setOThpCntPT((ArrayList<OThpCntP>) oThpIpcC.getOThpCntPT().stream().filter(o -> !isEmpty(o.getOThpCntS())).collect(Collectors.toList()));
        oThpIpcC.setOThpLcnPT((ArrayList<OThpLcnP>) oThpIpcC.getOThpLcnPT().stream().filter(o -> !isEmpty(o.getOThpLcnS())).collect(Collectors.toList()));
        oThpIpcC.setOThpLgrPT((ArrayList<OThpLgrP>) oThpIpcC.getOThpLgrPT().stream().filter(o -> !isEmpty(o.getOThpLgrS())).collect(Collectors.toList()));
        oThpIpcC.setOThpPcePT((ArrayList<OThpPceP>) oThpIpcC.getOThpPcePT().stream().filter(o -> !isEmpty(o.getOThpPceS())).collect(Collectors.toList()));
        oThpIpcC.setOThpPcmPT((ArrayList<OThpPcmP>) oThpIpcC.getOThpPcmPT().stream().filter(o -> !isEmpty(o.getOThpPcmS())).collect(Collectors.toList()));
        oThpIpcC.setOThpShlPT((ArrayList<OThpShlP>) oThpIpcC.getOThpShlPT().stream().filter(o -> !isEmpty(o.getOThpShlS())).collect(Collectors.toList()));
        oThpIpcC.setOThpTmnPT((ArrayList<OThpTmnP>) oThpIpcC.getOThpTmnPT().stream().filter(o -> !isEmpty(o.getOThpTmnS())).collect(Collectors.toList()));
        oThpIpcC.setOThpTxlPT((ArrayList<OThpTxlP>) oThpIpcC.getOThpTxlPT().stream().filter(o -> !isEmpty(o.getOThpTxlS())).collect(Collectors.toList()));
        oThpIpcC.setOThpUniPT((ArrayList<OThpUniP>) oThpIpcC.getOThpUniPT().stream().filter(o -> !isEmpty(o.getOThpUniS())).collect(Collectors.toList()));
    }

    private boolean isEmpty(Object object) {
        try {
            boolean empty = true;
            for (int i = 0; i < object.getClass().getDeclaredFields().length && empty; i++) {
                Field field = object.getClass().getDeclaredFields()[i];
                field.setAccessible(true);
                if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                    empty = field.get(object) == null;
                }
            }
            return empty;
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}
