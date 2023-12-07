package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import com.mapfre.tron.api.tsy.client.api.TreasuryApi;
import com.mapfre.tron.api.tsy.client.model.InPolicy;
import com.mapfre.tron.api.tsy.client.model.ORcpErcPC;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The ReceiptInformationSfv SfvBean implementation.
 * Get receipts information from a policy
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("ReceiptInformationSfv")
public class ReceiptInformationSfvBeanImpl extends SfvWithConditionalBeanBase {

	@Autowired
	protected TreasuryApi treasuryApi;

	@Override
	public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal,
			Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("ReceiptInformationSfvBeanImpl.execute start execute operation...");
		}

		SfvOut lvSfvOut = sfvMapper.map(in);
		String plyValRcp = getString(in.getParameters().get("NUM_POLIZA"));
		boolean skipError = params.containsKey("skipError") && Boolean.parseBoolean(params.get("skipError").toString());
		
		if (StringUtils.isEmpty(plyValRcp)) {
			Message lvMessagesItem = getMessage("NUM_POLIZA", 3, 20000, null);
			lvSfvOut.addMessagesItem(lvMessagesItem);
		} else {
			try {
				InPolicy inpr = createInPolicy(in);
			
				List<ORcpErcPC> rcps = treasuryApi.getReceiptByPlyv1(cmpVal.intValueExact(), usrVal, lngVal, plyValRcp, inpr, null, null, null, null);
				innerData.set("ReceiptInformationSfv", rcps);
				
				if (rcps != null && !rcps.isEmpty()) {
					lvSfvOut.getParameters().put("NUM_RECIBO", rcps.get(0).getORcpErcC().getORcpPmrP().getORcpPmrS().getRcpVal());
					lvSfvOut.getParameters().put("COD_MONEDA", rcps.get(0).getORcpErcC().getORcpPmrP().getORcpPmrS().getCrnVal());
				}
				
			} catch (RestClientResponseException e) {
				processApiError(lvSfvOut, skipError, e);
			} catch (Exception e) {
				String msg = String.format("Error calling treasuryApi.getReceiptByPlyv1 %s", e.getMessage());
				log.error(msg);
				Message lvMessagesItem = getMessage(msg, 3, 400, null);
				lvSfvOut.addMessagesItem(lvMessagesItem);
			}
		}

		return lvSfvOut;
	}

	private InPolicy createInPolicy(final SfvIn in) {
		InPolicy inpr = new InPolicy();
		inpr.setAplEnrSqn(in.getParameters().containsKey("NUM_SPTO_APLI") ? Integer.parseInt(getString(in.getParameters().get("NUM_SPTO_APLI"))) : 0);
		inpr.setAplVal(in.getParameters().containsKey("NUM_APLI") ? Integer.parseInt(getString(in.getParameters().get("NUM_APLI"))) : 0);
		inpr.setEnrSqn(in.getParameters().containsKey("NUM_SPTO") ? getString(in.getParameters().get("NUM_SPTO")) : "0");
		return inpr;
	}
}
