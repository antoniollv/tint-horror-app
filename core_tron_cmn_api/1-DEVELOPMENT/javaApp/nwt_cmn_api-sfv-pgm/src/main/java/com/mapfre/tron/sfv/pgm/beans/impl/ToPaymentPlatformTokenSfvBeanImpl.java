package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mapfre.tron.api.cache.CacheableAttribute;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import lombok.extern.slf4j.Slf4j;

/**
 * The ToPaymentPlatformTokenSfvBeanImpl SfvBean implementation.
 * Prepare data to payment platform for token
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("ToPaymentPlatformTokenSfv")
public class ToPaymentPlatformTokenSfvBeanImpl extends SfvWithConditionalBeanBase {

	private static final String MCA_ASEGURADO_IGUAL_TOMADOR = "MCA_ASEGURADO_IGUAL_TOMADOR";
	private static final String COD_LOCALIDAD = "COD_LOCALIDAD";
	private static final String LOCALIDAD = "LOCALIDAD";
	private static final String COD_PROV = "COD_PROV";
	private static final String MEDIO_PAGO = "MEDIO_PAGO";
	private static final String COD_PAIS = "COD_PAIS";
	private static final String PROV = "PROV";
	private static final String TOKEN = "TOKEN";
	private static final String TIPO = "TIPO_CC";
	
	@Autowired private CacheableAttribute cacheableAttribute;

	@Override @SuppressWarnings("unchecked")
	public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal,
			Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("ToPaymentPlatformTokenSfvBeanImpl.execute start execute operation...");
		}

		SfvOut lvSfvOut = sfvMapper.map(in);
		
		List<Map<String, String>> mediosPago = (List<Map<String, String>>)in.getParameters().get("MEDIOS_PAGO");
		
		if (mediosPago != null) {
			Optional<Map<String, String>> mp = mediosPago.stream().filter(x -> x.containsKey(MEDIO_PAGO) && "TA".equals(x.get(MEDIO_PAGO)) && x.containsKey(TOKEN)).findFirst();
			if (mp.isPresent()) {
				lvSfvOut.getParameters().put(TOKEN, mp.get().get(TOKEN));
				lvSfvOut.getParameters().put(TIPO, mp.get().get(TIPO));
				if (!in.getParameters().containsKey(MCA_ASEGURADO_IGUAL_TOMADOR)) {
					lvSfvOut.getParameters().put(MCA_ASEGURADO_IGUAL_TOMADOR, "S");
				}
			}
		}
		
		completeProv(in, lvSfvOut, cmpVal, lngVal);
		comlpeteLoc(in, lvSfvOut, cmpVal, lngVal, usrVal);

		return lvSfvOut;
	}
	
	private void completeProv(final SfvIn in, final SfvOut out, final BigDecimal cmpVal, final String lngVal) {
		if (!in.getParameters().containsKey(PROV) || StringUtils.isEmpty(in.getParameters().get(PROV).toString())) {
			out.getParameters().put(PROV, cacheableAttribute.getZoneThree(getString(in.getParameters().get(COD_PAIS)), getBD(in.getParameters().get(COD_PROV)), lngVal, cmpVal).getPvcNam());
		}
	}

	private void comlpeteLoc(final SfvIn in, final SfvOut out, final BigDecimal cmpVal, final String lngVal, final String usrVal) {
		if (!in.getParameters().containsKey(LOCALIDAD) || StringUtils.isEmpty(in.getParameters().get(LOCALIDAD).toString())) {
			out.getParameters().put(LOCALIDAD, cacheableAttribute.getZoneFour(cmpVal, usrVal, lngVal, getString(in.getParameters().get(COD_PAIS)), getBD(in.getParameters().get(COD_PROV)), getBD(in.getParameters().get(COD_LOCALIDAD))).getTwnNam());
		}
	}
}
