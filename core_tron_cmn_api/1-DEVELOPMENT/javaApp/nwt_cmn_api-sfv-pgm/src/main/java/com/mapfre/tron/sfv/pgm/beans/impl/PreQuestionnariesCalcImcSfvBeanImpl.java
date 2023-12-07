package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.sfv.bo.OCmnSfvS;
import com.mapfre.nwt.trn.cmn.sfv.sr.ISrCmnSfvVld;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrCPT;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.tron.api.isu.client.api.IssueApi;
import com.mapfre.tron.api.isu.client.model.OPlyAtrS;
import com.mapfre.tron.sfv.bo.BasicData;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.OFldNamValDocQueryS;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.dl.IDlP2300205DAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("PreQuestionnariesCalcImcSfv")
public class PreQuestionnariesCalcImcSfvBeanImpl extends SfvWithConditionalBeanBase {

	private static final String TIP_COMPLETADO_IN = "TIP_COMPLETADO_FORMULARIO";

	@Autowired
	private IDlP2300205DAO iDlP2300205DAO;
	
	@Autowired
    protected ISrCmnSfvVld iSrCmnSfvVld;
	
	@Autowired
	protected IssueApi issueApi;

	@Override
	protected SfvOut logic(SfvIn in, BigDecimal cmpVal, String usrVal, String lngVal, Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("PreQuestionnariesCalcImcSfv.execute start execute operation...");
		}

		SfvOut lvSfvOut = sfvMapper.map(in);

		String numPoliza = getString(in.getParameters().get("NUM_PRESUPUESTO"));
		BigDecimal enrSqn = getBD(params.get("NUM_SPTO"));
		BigDecimal rskVal = getBD(params.get("NUM_RIESGO"));
		String code = getString(params.get("NUM_FORMULARIO"));
		String steIdn = getString(params.get("PASO"));
		String fldNam = getString(params.get("CAMPO"));
		String scrSci = getString(params.get("SECCION"));

		try {
			BigDecimal frlVal = getBD(code);
			
			List<OFldNamValDocQueryS> datos = iDlP2300205DAO.getDocNam(cmpVal, numPoliza, enrSqn, rskVal, frlVal);
			if (!noHayDatos(datos, code)) {
				Map<String, String> lstMap = new HashMap<>();
			
				parseaLista(lstMap, datos);
				
				OCmnSfvS lvOCmnSfvS = new OCmnSfvS();
				lvOCmnSfvS.setCmpVal(cmpVal);
				lvOCmnSfvS.setFlwIdn(in.getFlwIdn());
				lvOCmnSfvS.setIdnKey(in.getIdnKey());
				lvOCmnSfvS.setSteIdn(steIdn);
				lvOCmnSfvS.setFldNam(fldNam);
				lvOCmnSfvS.setScrSci(scrSci);
		        lvOCmnSfvS.setLngVal(lngVal);
		        lvOCmnSfvS.setUsrVal(usrVal);

		        List<OPlyAtrP> lvOPlyAtrPT = getOPlyAtrPT(lstMap);

		        // -------------- invoking BE SR...
		        OPlyAtrCPT lvOPlyAtrCPT = iSrCmnSfvVld.vldFlw(lvOCmnSfvS, lvOPlyAtrPT);
		        NwtUtils lvNwtUtils = new NwtUtils();
	            lvNwtUtils.isTrmOk(lvOPlyAtrCPT, "iSrCmnSfvVld.vldFlw");
	            List<OPlyAtrS> vals = new ArrayList<>();
	            if (lvOPlyAtrCPT.getOPlyAtrPT() != null && !lvOPlyAtrCPT.getOPlyAtrPT().isEmpty()) {
		            for (OPlyAtrP p : lvOPlyAtrCPT.getOPlyAtrPT()) {
		            	OPlyAtrS s = new OPlyAtrS();
		            	s.setFldNam(p.getOPlyAtrS().getFldNam());
		            	s.setFldValVal(p.getOPlyAtrS().getFldValVal());
		            	vals.add(s);
		            }
	            }
	            
				// Salvar respuesta
				BasicData d = BasicData.builder().cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal).build();
				salvaValores(d, numPoliza, enrSqn, rskVal, frlVal, lstMap, vals);
			}
		} catch (Exception e) {
			processException(lvSfvOut, e);
		}

		return lvSfvOut;
	}

	private void parseaLista(Map<String, String> lstMap, List<OFldNamValDocQueryS> datos) {
		for (OFldNamValDocQueryS d : datos) {
			lstMap.put(d.getFldNam(), d.getFldVal());
		}
	}

	private boolean noHayDatos(List<OFldNamValDocQueryS> datos, String code) {
		return datos.isEmpty() || (datos.size() == 1 && datos.get(0).getFldNam().equals(TIP_COMPLETADO_IN + "_" + code));
	}

	private void salvaValores(BasicData d, String smnVal, BigDecimal enrSqn, BigDecimal rskVal, BigDecimal frlVal, Map<String, String> lstMap, List<OPlyAtrS> oPlyAtrS) {
		// Quitar las respuestas que se van a modificar
		for (OPlyAtrS f : oPlyAtrS) {
			lstMap.remove(f.getFldNam());
		}
		
		// Incluir las respuestas que no se vana modificar		
		for (Map.Entry<String, String> e : lstMap.entrySet()) {
			OPlyAtrS f = new OPlyAtrS();
			f.setFldNam(e.getKey());
			f.setFldValVal(e.getValue());
			oPlyAtrS.add(f);
		}
		
		// Completar los datos de las respuestas
		for (OPlyAtrS f : oPlyAtrS) {
			f.setCmpVal(d.getCmpVal());
			f.setQtnVal(smnVal);
			f.setEnrSqn(enrSqn);
			f.setRskVal(rskVal);
			f.setFrlVal(frlVal);
		}

		// Salvar
		issueApi.postFormularyQuotation(d.getCmpVal().intValueExact(), d.getUsrVal(), d.getLngVal(), oPlyAtrS);
	}
	
	protected void processException(SfvOut lvSfvOut, Exception e) {
		log.debug(e.toString());
		Message lvMessagesItem = null;
		if (e instanceof RestClientResponseException) {
			RestClientResponseException ee = (RestClientResponseException)e;
			lvMessagesItem = getMessage(ee.getResponseBodyAsString(), 3, ee.getRawStatusCode(), null);
		} else if (e instanceof NwtException) {
			NwtException ee = (NwtException)e;
			lvMessagesItem = getMessage(ee.getErrors().get(0).getErrVal() + "-" + ee.getErrors().get(0).getErrNam(), 3, 500, null);
		} else {
			lvMessagesItem = getMessage(e.getMessage(), 3, 500, null);
		}
		lvSfvOut.addMessagesItem(lvMessagesItem);
	}
	
	private List<OPlyAtrP> getOPlyAtrPT(final Map<String, String> sfvIn) {
        return sfvIn.entrySet().stream()
        		.filter(o -> o.getValue() == null || o.getValue() instanceof String)
        		.map(o -> {
		            OPlyAtrP lvOPlyAtrP = new OPlyAtrP();
		            com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS lvOPlyAtrS = new com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS();
		            lvOPlyAtrS.setFldNam(o.getKey());
		            String s = getString(o.getValue());
		            if (s != null && s.length() > 80) {
		            	s = s.substring(0, 80);
		            }
		            lvOPlyAtrS.setFldValVal(s);
		            lvOPlyAtrP.setOPlyAtrS(lvOPlyAtrS);
		
		            return lvOPlyAtrP;
        }).collect(Collectors.toList());
    }

}
