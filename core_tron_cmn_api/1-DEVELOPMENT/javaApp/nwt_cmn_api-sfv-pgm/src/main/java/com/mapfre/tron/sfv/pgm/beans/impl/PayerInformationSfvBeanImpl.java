package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mapfre.tron.api.isu.client.model.OPlyIneP;
import com.mapfre.tron.api.isu.client.model.OPlyPlyC;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The PayerInformationSfv SfvBean implementation.
 * Get the alternative insured party from the policy
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("PayerInformationSfv")
public class PayerInformationSfvBeanImpl extends SfvWithConditionalBeanBase {

	@Override
	public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal,
			Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("PayerInformationSfvBeanImpl.execute start execute operation...");
		}

		SfvOut lvSfvOut = sfvMapper.map(in);
		OPlyPlyC oPlyPlyC = (OPlyPlyC)innerData.get("PolicyInformationSfv");
		
		if (oPlyPlyC == null) {
			Message lvMessagesItem = getMessage("PAYER", 3, 20000, null);
			lvSfvOut.addMessagesItem(lvMessagesItem);
		} else {

			Optional<OPlyIneP> payer = oPlyPlyC.getOPlyInePT().stream().filter(x -> params.get("bnfTypValAlt").equals(x.getOPlyIneS().getBnfTypVal())).findFirst();
			if (!payer.isPresent()) {
				payer = oPlyPlyC.getOPlyInePT().stream().filter(x -> params.get("bnfTypVal").equals(x.getOPlyIneS().getBnfTypVal())).findFirst();
			}
			
			if (payer.isPresent()) {
				innerData.set("PayerInformationSfv", payer.get());
				String tipDocum = payer.get().getOPlyIneS().getThpDcmTypVal();
				String codDocum = payer.get().getOPlyIneS().getThpDcmVal();
				lvSfvOut.getParameters().put("TIP_DOCUM", tipDocum);
				lvSfvOut.getParameters().put("COD_DOCUM", codDocum);
				lvSfvOut.getParameters().put("NOM_TITU", payer.get().getOPlyIneS().getCpeNam());
				
				getPcmSqnVal(lvSfvOut, oPlyPlyC, tipDocum, codDocum);
			} else {
				Message lvMessagesItem = getMessage("PAYER_INFORMATION", 3, -20001, null);
				lvSfvOut.addMessagesItem(lvMessagesItem);
			}
		}
		return lvSfvOut;
	}

	private void getPcmSqnVal(SfvOut lvSfvOut, OPlyPlyC oPlyPlyC, String tipDocum, String codDocum) {
		BigDecimal pcmSqnVal = null;
		Optional<OPlyIneP> payer0 = oPlyPlyC.getOPlyInePT().stream().filter(x -> codDocum.equals(x.getOPlyIneS().getThpDcmVal()) && tipDocum.equals(x.getOPlyIneS().getThpDcmTypVal())).findFirst();
		if (payer0.isPresent()) {
			pcmSqnVal = payer0.get().getOPlyIneS().getPcmSqnVal();
			if (pcmSqnVal == null) {
				pcmSqnVal = payer0.get().getOPlyIneS().getSqnAcoCrdVal();
			}
		} 
		
		if (pcmSqnVal == null) {
			Optional<OPlyIneP> payerRkc = oPlyPlyC.getOPlyRkcCT().get(0).getOPlyRkcC().getOPlyInePT().stream().filter(x -> codDocum.equals(x.getOPlyIneS().getThpDcmVal()) && tipDocum.equals(x.getOPlyIneS().getThpDcmTypVal())).findFirst();
			if (payerRkc.isPresent()) {
				pcmSqnVal = payerRkc.get().getOPlyIneS().getPcmSqnVal();
				if (pcmSqnVal == null) {
					pcmSqnVal = payerRkc.get().getOPlyIneS().getSqnAcoCrdVal();
				}
			}
		}
		
		if (pcmSqnVal != null) {
			lvSfvOut.getParameters().put("PCM_SQN_VAL", pcmSqnVal);
		}
	}
}
