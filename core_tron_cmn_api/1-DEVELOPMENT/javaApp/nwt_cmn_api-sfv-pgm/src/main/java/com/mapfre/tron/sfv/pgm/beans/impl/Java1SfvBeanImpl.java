package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mapfre.tron.api.thp.client.api.InsuredPartyApi;
import com.mapfre.tron.api.thp.client.model.OThpIpcPC;
import com.mapfre.tron.api.thp.client.model.OThpPrsS;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.ISfvBean;
import com.mapfre.tron.sfv.pgm.mapper.SfvMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * The Java1 SfvBean implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("BeanJava1")
public class Java1SfvBeanImpl implements ISfvBean {

    @Autowired
    protected SfvMapper sfvMapper;

    @Autowired
    protected InsuredPartyApi insuredPartyApi;

    @Override
    public SfvOut execute(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal, Map<String, Object> params) {
        if (log.isInfoEnabled()) {
            log.info("Java1SfvBeanImpl start execute operation...");
        }

        SfvOut lvSfvOut = sfvMapper.map(in);

        try {
            OThpIpcPC oThpIpcPC = insuredPartyApi.getInsuredpartyv1(
                    cmpVal.intValueExact(),
                    usrVal,
                    lngVal,
                    in.getParameters().get("thpDcmTypVal").toString(),
                    in.getParameters().get("thpDcmVal").toString());

            if (oThpIpcPC != null && oThpIpcPC.getOThpIpcC() != null && oThpIpcPC.getOThpIpcC().getOThpPrsP() != null && oThpIpcPC.getOThpIpcC().getOThpPrsP().getOThpPrsS() != null) {
                OThpPrsS lvOThpPrsS = oThpIpcPC.getOThpIpcC().getOThpPrsP().getOThpPrsS();
                lvSfvOut.getParameters().put("thpNam", lvOThpPrsS.getThpNam());
                lvSfvOut.getParameters().put("thpScnNam", lvOThpPrsS.getThpScnNam());
                lvSfvOut.getParameters().put("thpFrsSrn", lvOThpPrsS.getThpFrsSrn());
                lvSfvOut.getParameters().put("thpScnSrn", lvOThpPrsS.getThpScnSrn());

                Message lvMessagesItem = getMessage("Success", Integer.valueOf(0), null);
                lvSfvOut.addMessagesItem(lvMessagesItem);

            } else {
                Message lvMessagesItem = getMessage("Error", Integer.valueOf(3), 20001);
                lvSfvOut.addMessagesItem(lvMessagesItem);
            }

        } catch (Exception e) {
            String msg = String.format("Error calling insuredPartyApi.getInsuredpartyv1 %s", e.getMessage());
            log.error(msg);
            Message lvMessagesItem = getMessage(msg, Integer.valueOf(3), 400);
            lvSfvOut.addMessagesItem(lvMessagesItem);
        }

        return lvSfvOut;
    }

    protected Message getMessage(final String msgTxtVal, final Integer msgTypVal, final Integer msgVal) {
        Message lvMessagesItem = new Message();
        lvMessagesItem.setMsgTxtVal(msgTxtVal);
        lvMessagesItem.setMsgTypVal(msgTypVal);
        lvMessagesItem.setMsgVal(msgVal);

        return lvMessagesItem;
    }
}
