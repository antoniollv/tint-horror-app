package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.OPlySmnDocQryS;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.dl.IDlPlySmnDocQryDAO;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * The PaymentMethods Sfv Bean implementation.
 * Return payment methos depending on num payments per year or previous payment method
 * CACHED
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("PaymentMethodsSfv")
public class PaymentMethodsSfvBeanImpl extends SfvWithConditionalBeanBase {
	
	@Autowired
    private IDlPlySmnDocQryDAO iDlPlySmnDocQryDAO;
	
	@Override
	public SfvOut logic(SfvIn in, BigDecimal cmpVal, String usrVal, String lngVal, Map<String, Object> params) {
		if (log.isInfoEnabled()) {
            log.info("PaymentMethodsSfvBeanImpl.execute start execute operation...");
        }
		
		SfvOut lvSfvOut = sfvMapper.map(in);
		
		String codPlanPago = getString(in.getParameters().get("COD_PLAN_PAGO"));
		String tipGestorAnt = getString(in.getParameters().get("MEDIO_PAGO_ANT"));
		String codRamo = getString(params.get("COD_RAMO"));
		
		List<OPlySmnDocQryS> respuesta = null;
		
		try {
			if (StringUtils.isNotEmpty(codPlanPago)) {				
				respuesta = iDlPlySmnDocQryDAO.getDocNam(cmpVal, new BigDecimal(codRamo), "PCM_MEDIOS_PAGO-" + codPlanPago);
			} else {
				respuesta = iDlPlySmnDocQryDAO.getDocNam(cmpVal, new BigDecimal(codRamo), "PCM_MEDIOS_PAGO-" + tipGestorAnt);
			}
			
			lvSfvOut.getParameters().put("MEDIOS_PAGO", respuesta);

		} catch (Exception e) {
			String msg = String.format("Error calling iDlPlySmnDocQryDAO %s", e.getMessage());
			log.error(msg);
			Message lvMessagesItem = getMessage(msg, 3, 500, null);
			lvSfvOut.addMessagesItem(lvMessagesItem);
		}
		
		return lvSfvOut;
	}
}
