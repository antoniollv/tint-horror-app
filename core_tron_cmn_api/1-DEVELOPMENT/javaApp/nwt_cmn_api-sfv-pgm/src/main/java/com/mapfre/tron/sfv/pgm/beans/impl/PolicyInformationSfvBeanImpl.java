package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import com.mapfre.tron.api.isu.client.api.IssueApi;
import com.mapfre.tron.api.isu.client.model.InPolicy;
import com.mapfre.tron.api.isu.client.model.OPlyPlyC;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The PolicyInformationSfv SfvBean implementation.
 * Get policy information data
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("PolicyInformationSfv")
public class PolicyInformationSfvBeanImpl extends SfvWithConditionalBeanBase {

	@Autowired
	protected IssueApi issueApi;

	@Override
	public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal,
			Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("PolicyInformationSfvBeanImpl.execute start execute operation...");
		}

		SfvOut lvSfvOut = sfvMapper.map(in);
		String plyVal = getString(in.getParameters().get("NUM_POLIZA"));
		boolean skipError = params.containsKey("skipError") && Boolean.parseBoolean(params.get("skipError").toString());

		if (StringUtils.isEmpty(plyVal)) {
			Message lvMessagesItem = getMessage("NUM_POLIZA", 3, 20000, null);
			lvSfvOut.addMessagesItem(lvMessagesItem);
		} else {

			try {
				InPolicy inp = new InPolicy();
				inp.setAplEnrSqn(in.getParameters().containsKey("NUM_SPTO_APLI") ? Integer.parseInt(getString(in.getParameters().get("NUM_SPTO_APLI"))) : 0);
				inp.setAplVal(in.getParameters().containsKey("NUM_APLI") ? Integer.parseInt(getString(in.getParameters().get("NUM_APLI"))) : 0);
				inp.setEnrSqn(in.getParameters().containsKey("NUM_SPTO") ? getString(in.getParameters().get("NUM_SPTO")) : "0");
				OPlyPlyC oPlyPlyC = issueApi.getPolicyInformation(cmpVal.intValueExact(), usrVal, lngVal, plyVal, inp, "2");

				innerData.set("PolicyInformationSfv", oPlyPlyC);
				
				lvSfvOut.getParameters().put("COD_FRACC_PAGO", oPlyPlyC.getOPlyGniP().getOPlyGniS().getPmsVal());

			} catch (RestClientResponseException e) {
				processApiError(lvSfvOut, skipError, e);
			} catch (Exception e) {
				String msg = String.format("Error calling issueApi.getPolicyInformation %s", e.getMessage());
				log.error(msg);
				Message lvMessagesItem = getMessage(msg, 3, 400, null);
				lvSfvOut.addMessagesItem(lvMessagesItem);
			}
		}
		return lvSfvOut;
	}
}
