package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.NofitFilesData;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.impl.cm.DocumentsComponent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("GenerateDocumentSfv")
public class GenerateDocumentSfvBeanImpl extends SfvWithConditionalBeanBase {
	
	private static final String NUM_PRESUPUESTO = "NUM_PRESUPUESTO";
	private static final String NUM_POLIZA = "NUM_POLIZA";

	
	@Autowired private DocumentsComponent docsQry;

	@SuppressWarnings("unchecked")
	@Override
	protected SfvOut logic(SfvIn in, BigDecimal cmpVal, String usrVal, String lngVal, Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("GenerateDocumentSfv.execute start execute operation...");
		}
		
		SfvOut lvSfvOut = sfvMapper.map(in);
		String lobVal = getString(params.get("COD_RAMO"));
		
		if (params.containsKey("oprIdnVal")) {
			List<String> actions = (List<String>)params.get("oprIdnVal");
			try {
				NofitFilesData nfd = NofitFilesData.builder().actions(actions).aplEnrSqn(getString(params.get("aplEnrSqn"))).aplVal(getString(params.get("aplVal")))
						.delVal(getString(params.get("delVal"))).enrSqn(getString(params.get("enrSqn"))).lobVal(lobVal)
						.oprFlt(getString(params.get("oprFlt"))).build();
				docsQry.generateDocument(cmpVal, usrVal, lngVal, nfd, getString(in.getParameters().get(NUM_PRESUPUESTO)), getString(in.getParameters().get(NUM_POLIZA)));
			} catch (Exception e) {
				processException(lvSfvOut, e);
			}
		}
		
		return lvSfvOut;
	}


	protected void processException(SfvOut lvSfvOut, Exception e) {
		log.debug(e.toString());
		Message lvMessagesItem = null;
		if (e instanceof RestClientResponseException) {
			RestClientResponseException ee = (RestClientResponseException)e;
			lvMessagesItem = getMessage(ee.getResponseBodyAsString(), 3, ee.getRawStatusCode(), null);
		} else {
			lvMessagesItem = getMessage(e.getMessage(), 3, 500, null);
		}
		lvSfvOut.addMessagesItem(lvMessagesItem);
	}
}
