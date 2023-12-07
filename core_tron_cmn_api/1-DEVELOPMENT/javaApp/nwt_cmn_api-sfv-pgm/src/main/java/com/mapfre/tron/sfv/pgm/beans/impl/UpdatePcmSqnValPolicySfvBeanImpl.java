package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The PaymentMethodSelectionSfv SfvBean implementation.
 * Select the payment method from de insured party payment methods that applies to the policy
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("UpdatePcmSqnValPolicySfv")
public class UpdatePcmSqnValPolicySfvBeanImpl extends SfvWithConditionalBeanBase {

	@Override
	public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal,
			Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("UpdatePcmSqnValPolicySfvBeanImpl.execute start execute operation...");
		}
		
		// A revisar Update policy

		return sfvMapper.map(in);
	}
}
