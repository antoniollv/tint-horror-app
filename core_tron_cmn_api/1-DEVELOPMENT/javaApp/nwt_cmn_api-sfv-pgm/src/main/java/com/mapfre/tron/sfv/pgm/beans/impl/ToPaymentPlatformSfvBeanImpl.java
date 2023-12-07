package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mapfre.tron.api.thp.client.model.OThpAdrS;
import com.mapfre.tron.api.thp.client.model.OThpIpcPC;
import com.mapfre.tron.api.thp.client.model.OThpPcmP;
import com.mapfre.tron.api.tsy.client.model.ORcpErcPC;
import com.mapfre.tron.api.tsy.client.model.ORcpPmrS;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The ToPaymentPlatformSfv SfvBean implementation.
 * Prepare data to payment platform previous to collect the first receipt
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("ToPaymentPlatformSfv")
public class ToPaymentPlatformSfvBeanImpl extends SfvWithConditionalBeanBase {

	@Override @SuppressWarnings("unchecked")
	public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal,
			Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("ToPaymentPlatformSfvBeanImpl.execute start execute operation...");
		}

		SfvOut lvSfvOut = sfvMapper.map(in);
		
		List<ORcpErcPC> rcps = (List<ORcpErcPC>)innerData.get("ReceiptInformationSfv");
		OThpIpcPC thp = (OThpIpcPC)innerData.get("InsuredPartySfv");
		String pcmSqnVal  = getString(in.getParameters().get("PCM_SQN_VAL"));
		
		if (thp == null || StringUtils.isEmpty(pcmSqnVal)) {
			Message lvMessagesItem = getMessage("PAYMENT_DATA", 3, 20000, null);
			lvSfvOut.addMessagesItem(lvMessagesItem);
		} else {
			// Receipt
			setReceiptData(lvSfvOut, rcps);
			// Personal data
			lvSfvOut.getParameters().put("PAIS", thp.getOThpIpcC().getOThpPrsP().getOThpPrsS().getNtnVal());
			lvSfvOut.getParameters().put("NOM_TITU", thp.getOThpIpcC().getOThpPrsP().getOThpPrsS().getCpeNam());
			// Address data
			setAddressData(lvSfvOut, thp);
			// Payment method data
			setPaymentMethodData(lvSfvOut, thp, pcmSqnVal);
		}
		return lvSfvOut;
	}

	private void setAddressData(SfvOut lvSfvOut, OThpIpcPC thp) {
		if (thp.getOThpIpcC().getOThpAdrPT() != null && !thp.getOThpIpcC().getOThpAdrPT().isEmpty()) {
			OThpAdrS adr = thp.getOThpIpcC().getOThpAdrPT().get(0).getOThpAdrS();
			lvSfvOut.getParameters().put("PROV", adr.getPvcNam());
			lvSfvOut.getParameters().put("LOCALIDAD", adr.getTwnNam());
			lvSfvOut.getParameters().put("COD_POSTAL", adr.getPslCodVal());
			lvSfvOut.getParameters().put("DIRECCION", adr.getAdrTxtVal());
			lvSfvOut.getParameters().put("NUMERO", getString(adr.getAdrNbrVal()));
			lvSfvOut.getParameters().put("EXTENSION", getString(adr.getExtAdrTxtVal()));
		}
	}

	private void setPaymentMethodData(SfvOut lvSfvOut, OThpIpcPC thp, String pcmSqnVal) {
		lvSfvOut.getParameters().put("TOKEN", "");
		if (StringUtils.isNotEmpty(pcmSqnVal)) {
			BigDecimal bpcmSqnVal = new BigDecimal(pcmSqnVal);
			Optional<OThpPcmP> pm= thp.getOThpIpcC().getOThpPcmPT().stream().filter(x -> bpcmSqnVal.equals(x.getOThpPcmS().getPcmSqnVal())).findFirst();
			if (pm.isPresent()) {
			    lvSfvOut.getParameters().put("TOKEN", pm.get().getOThpPcmS().getTknVal());
			    lvSfvOut.getParameters().put("NOM_TITU", pm.get().getOThpPcmS().getHlrNam());
			    lvSfvOut.getParameters().put("TIPO_CC", pm.get().getOThpPcmS().getPcmCssNam());
			}
		}
	}

	private void setReceiptData(SfvOut lvSfvOut, List<ORcpErcPC> rcps) {
		if (rcps != null) {
			ORcpPmrS rcp = rcps.get(0).getORcpErcC().getORcpPmrP().getORcpPmrS();
			lvSfvOut.getParameters().put("CANTIDAD", getString(rcp.getPndAmn()));
			lvSfvOut.getParameters().put("MONEDA", rcp.getCrnNam());
			lvSfvOut.getParameters().put("COD_MONEDA", rcp.getCrnVal());
			lvSfvOut.getParameters().put("NUM_RECIBO", getString(rcp.getRcpVal()));
		}
	}
}
