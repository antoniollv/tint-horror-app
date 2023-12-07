package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.OPlySmnDocQryS;
import com.mapfre.tron.sfv.bo.PlyDocument;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.impl.cm.DocumentsComponent;
import com.mapfre.tron.sfv.pgm.dl.IDlP2300205DAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("CheckDocumentsSfv")
public class CheckDocumentsSfvBeanImpl extends SfvWithConditionalBeanBase {
	
	private static final String A_FIRMAR = "A_FIRMAR";
	private static final String NUM_COTIZACION = "NUM_COTIZACION";
	private static final String NUM_POLIZA = "NUM_POLIZA";
	private static final String NUM_PRESUPUESTO = "NUM_PRESUPUESTO";
	private static final BigDecimal LOB_VAL = new BigDecimal("999");
	private static final String APL_VAL = "aplVal";
	private static final String ENR_SQN = "enrSqn";
	private static final String RSK_VAL = "rskVal";
	private static final String SALUD = "SALUD";
	private static final String FINACIERO = "FINACIERO";
	private static final String SALUD_ID = "SALUD_ID";
	private static final String FINACIERO_ID = "FINACIERO_ID";
	
	@Autowired private DocumentsComponent docsQry;

	@Autowired private IDlP2300205DAO frmQry;
	
	@SuppressWarnings("unchecked")
	@Override
	protected SfvOut logic(SfvIn in, BigDecimal cmpVal, String usrVal, String lngVal, Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("CheckDocumentsSfv.execute start execute operation...");
		}
		
		SfvOut lvSfvOut = sfvMapper.map(in);
		String idStep = "" + getSteIdnId(in.getPosition().getSteIdn());
		List<String> listsToSign = (List<String>)params.get(A_FIRMAR);
		List<Integer> saludForms = (List<Integer>)params.get(SALUD);
		List<Integer> financieroForms = (List<Integer>)params.get(FINACIERO);
		BigDecimal aplVal = getBD(params.get(APL_VAL));
		BigDecimal enrSqn = getBD(params.get(ENR_SQN));
		BigDecimal rskVal = getBD(params.get(RSK_VAL));
		String plyVal = getPlyVal(in);

		try {
			boolean bcs = false;
			for (int i = 0; i < saludForms.size() && !bcs; i++) {
				Integer chk = frmQry.getCountDocNam(cmpVal, plyVal, enrSqn, rskVal, new BigDecimal(saludForms.get(i)));
				bcs = chk != null && chk > 0;
			}
			Pair<String, Boolean> cs = Pair.of(getString(params.get(SALUD_ID)), bcs);
			boolean bcf = false;
			for (int i = 0; i < financieroForms.size() && !bcf; i++) {
				Integer chk = frmQry.getCountDocNam(cmpVal, plyVal, enrSqn, rskVal, new BigDecimal(financieroForms.get(i)));
				bcf = chk != null && chk > 0;
			}
			Pair<String, Boolean> cf = Pair.of(getString(params.get(FINACIERO_ID)), bcf);
		
			List<OPlySmnDocQryS> docsMap = docsQry.getDocsMap(cmpVal, LOB_VAL);
			List<OPlySmnDocQryS> docs = docsQry.getDocsByStep(cmpVal, LOB_VAL, idStep);
			
			List<PlyDocument> gddocs = docsQry.getDocsByQuotation(cmpVal, usrVal, plyVal, aplVal.intValueExact());
			
			List<OPlySmnDocQryS> pdocs = docsQry.getMandatoryDocsPending(docs, gddocs, docsMap);
			
			for (OPlySmnDocQryS d : pdocs) {
				lvSfvOut.addMessagesItem(getMessage(d.getRowNam(), 3, 1001001, null));
			}
			
			pdocs = docsQry.getSignedDocsPending(docs, gddocs, docsMap, listsToSign, cs, cf);

			for (OPlySmnDocQryS d : pdocs) {
				lvSfvOut.addMessagesItem(getMessage(d.getRowNam(), 3, 1001002, null));
			}
		
		} catch(Exception e) {
			 processException(lvSfvOut, e);
		}
		
		return lvSfvOut;
	}

	private String getPlyVal(SfvIn in) {
		String plyVal = getString(in.getParameters().get(NUM_POLIZA));
		if (StringUtils.isEmpty(plyVal)) {
			 plyVal = getString(in.getParameters().get(NUM_PRESUPUESTO));
		}
		if (StringUtils.isEmpty(plyVal)) {
			 plyVal = getString(in.getParameters().get(NUM_COTIZACION));
		}
		return plyVal;
	}

	protected void processException(SfvOut lvSfvOut, Exception e) {
		log.debug(e.toString());
		Message lvMessagesItem = null;
		if (e instanceof NwtException) {
			NwtException ee = (NwtException)e;
			lvMessagesItem = getMessage(ee.getErrors().get(0).getErrVal() + "-" + ee.getErrors().get(0).getErrNam(), 3, 500, null);
		} else if (e instanceof RestClientResponseException) {
			RestClientResponseException ee = (RestClientResponseException)e;
			lvMessagesItem = getMessage(ee.getResponseBodyAsString(), 3, ee.getRawStatusCode(), null);
		} else {
			lvMessagesItem = getMessage(e.getMessage(), 3, 500, null);
		}
		lvSfvOut.addMessagesItem(lvMessagesItem);
	}
}
