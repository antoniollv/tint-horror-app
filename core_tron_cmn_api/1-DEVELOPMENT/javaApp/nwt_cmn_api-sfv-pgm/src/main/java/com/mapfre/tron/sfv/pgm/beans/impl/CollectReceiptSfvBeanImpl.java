package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import com.mapfre.tron.api.tsy.client.api.TreasuryApi;
import com.mapfre.tron.api.tsy.client.model.InReceiptCollection;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The CollectReceiptSfvBeanImpl SfvBean implementation.
 * Collect receipt
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("CollectReceiptSfv")
public class CollectReceiptSfvBeanImpl extends SfvWithConditionalBeanBase {

	@Autowired
	protected TreasuryApi treasuryApi;

	@Override
	public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal,
			Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("CollectReceiptSfvBeanImpl.execute start execute operation...");
		}

		SfvOut lvSfvOut = sfvMapper.map(in);
		String rcpVal = getString(in.getParameters().get("NUM_RECIBO"));
		String crnVal = getString(in.getParameters().get("COD_MONEDA"));
		
		if (StringUtils.isEmpty(rcpVal) || StringUtils.isEmpty(crnVal)) {
			Message lvMessagesItem = getMessage("NUM_RECIBO/COD_MONEDA", 3, 20000, null);
			lvSfvOut.addMessagesItem(lvMessagesItem);
		} else {

			try {
				InReceiptCollection inc = new InReceiptCollection();
				inc.setCshVal(getString(params.get("cshVal")));
				inc.setCloCrnVal(Integer.parseInt(crnVal));
				inc.setCloTnsBlcVal(Integer.parseInt(rcpVal)); // A revisar
				
				treasuryApi.postReceiptCollect(cmpVal.intValueExact(), usrVal, lngVal, Integer.parseInt(rcpVal), inc);

			} catch (RestClientResponseException e) {
				processApiError(lvSfvOut, false, e);
			} catch (Exception e) {
				String msg = String.format("Error calling treasuryApi.postReceiptCollect %s", e.getMessage());
				log.error(msg);
				Message lvMessagesItem = getMessage(msg, 3, 400, null);
				lvSfvOut.addMessagesItem(lvMessagesItem);
			}
		}
		return lvSfvOut;
	}
}
