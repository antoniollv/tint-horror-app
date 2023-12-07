package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import com.mapfre.nwt.commons.utils.NwtUtils;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.sfv.sr.ISrCmnSfvVld;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrCPT;
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrP;
import com.mapfre.tron.api.isu.client.api.IssueApi;
import com.mapfre.tron.api.isu.client.model.OPlyAtrS;
import com.mapfre.tron.sfv.bo.BasicData;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.OFldNamValDocQueryS;
import com.mapfre.tron.sfv.bo.OFlrDfn;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.dl.IDlA2300200DAO;
import com.mapfre.tron.sfv.pgm.dl.IDlP2300205DAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("QuestionnariesStatusSfv")
public class QuestionnariesStatusSfvBeanImpl extends SfvWithConditionalBeanBase {

	private static final String TIP_COMPLETADO_IN = "TIP_COMPLETADO_FORMULARIO";
	private static final String FEC_COMPLETADO_IN = "FEC_CUMPLIMENTACION";
	private static final String FEC_VALIDEZ_IN = "FEC_VALIDEZ_FORMULARIO";
	private static final String FEC_SITU = "FEC_SITU";
	private static final String NOM_TIP_SITU = "NOM_TIP_SITU";
	private static final String TIP_SITU_PENDIENTE = "TIP_SITU_PENDIENTE";
	private static final String TIP_SITU_COMPLETADO = "TIP_SITU_COMPLETADO";

	@Autowired
	private IDlP2300205DAO iDlP2300205DAO;
	
	@Autowired
	private IDlA2300200DAO iDlA2300200DAO;
	
	@Autowired
    protected ISrCmnSfvVld iSrCmnSfvVld;
	
	@Autowired
	protected IssueApi issueApi;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

	@SuppressWarnings("unchecked")
	@Override
	protected SfvOut logic(SfvIn in, BigDecimal cmpVal, String usrVal, String lngVal, Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("QuestionnariesStatusSfvBeanImpl.execute start execute operation...");
		}

		SfvOut lvSfvOut = sfvMapper.map(in);

		String numPoliza = getString(in.getParameters().get("NUM_POLIZA"));
		BigDecimal enrSqn = getBD(params.get("NUM_SPTO"));
		BigDecimal rskVal = getBD(params.get("NUM_RIESGO"));
		BigDecimal lobVal = getBD(params.get("COD_RAMO"));
		BigDecimal tipMtoBatch = getBD(params.get("TIP_MVTO_BATCH"));

		List<Map<String, String>> cuestionarios = (List<Map<String, String>>) in.getParameters().get("CUESTIONARIOS");

		List<Map<String, String>> salida = new ArrayList<>();

		try {
			// Recorrer la lista de cuestionarios e ir llamando a las consultas
			for (Map<String, String> cuestionario : cuestionarios) {
				String code = cuestionario.get("CODE");
				BigDecimal frlVal = getBD(code);
				Map<String, String> estructura = new HashMap<>();
				salida.add(estructura);
	
				estructura.put("CODE", cuestionario.get("CODE"));
				estructura.put("NOM_CODE", cuestionario.get("NOM_CODE"));
	
				List<OPlyAtrS> oPlyAtrS = new ArrayList<>();
				Map<String, String> lstMap = new HashMap<>();
				List<OFldNamValDocQueryS> datos = iDlP2300205DAO.getDocNam(cmpVal, numPoliza, enrSqn, rskVal, frlVal);
				if (noHayDatos(datos, code)) {
					String nomTipSitu = getString(params.get(TIP_SITU_PENDIENTE));
					
					OPlyAtrS f = new OPlyAtrS();
					f.setFldNam(TIP_COMPLETADO_IN + "_" + code);
					f.setFldValVal(nomTipSitu);
					oPlyAtrS.add(f);
					
					estructura.put(NOM_TIP_SITU, nomTipSitu);
					estructura.put(FEC_SITU, "");
				} else {
					parseaLista(lstMap, datos);

					OFlrDfn oFlrDfn = iDlA2300200DAO.getFrlDfn(cmpVal, frlVal);
					
					String fecSitu = "";
					String fecValidez = "";
					String nomTipSitu = "";
					if (StringUtils.isNotEmpty(oFlrDfn.getPgmNam())) {
						// LLamar al PL de validacion del
						com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS inAtr = new com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS();
						inAtr.setCmpVal(cmpVal);
						inAtr.setQtnVal(numPoliza);
						inAtr.setEnrSqn(enrSqn);
						inAtr.setRskVal(rskVal);
						inAtr.setFrlVal(frlVal);
						inAtr.setLobVal(lobVal);
						inAtr.setUsrVal(usrVal);
						
						OPlyAtrCPT datosPl = iSrCmnSfvVld.vldFlr(inAtr, tipMtoBatch, oFlrDfn.getPgmNam());
						NwtUtils lvNwtUtils = new NwtUtils();
			            lvNwtUtils.isTrmOk(datosPl, "iSrCmnSfvVld.vldFlr");
						
			            nomTipSitu = getSalida(datosPl, TIP_COMPLETADO_IN);
			            
			            String fecc = getSalida(datosPl, FEC_COMPLETADO_IN);
			            Pair<String, String> pfecc = getFechas(fecc);
			            fecSitu = pfecc.getLeft();
			        	fecValidez = pfecc.getRight();
			            
					} else {
						nomTipSitu = getString(params.get(TIP_SITU_COMPLETADO));
						fecSitu = LocalDate.now().format(formatter);
					}
					
					estructura.put(NOM_TIP_SITU, nomTipSitu);
					estructura.put(FEC_SITU, fecSitu);
					
					OPlyAtrS ff = new OPlyAtrS();
					ff.setFldNam(TIP_COMPLETADO_IN + "_" + code);
					ff.setFldValVal(nomTipSitu);
					oPlyAtrS.add(ff);
					
					if (noHayFechaCompletado(lstMap, code)) {
						addFechas(code, oPlyAtrS, fecSitu, fecValidez);
				    } else {
				    	estructura.put(FEC_SITU, lstMap.get(FEC_COMPLETADO_IN + "_" + code));
				    }					
				}
				
				// Salvar respuesta
				BasicData d = BasicData.builder().cmpVal(cmpVal).lngVal(lngVal).usrVal(usrVal).build();
				salvaValores(d, numPoliza, enrSqn, rskVal, frlVal, lstMap, oPlyAtrS);				
			}
		} catch (Exception e) {
			processException(lvSfvOut, e);
		}

		lvSfvOut.getParameters().put("QUESTIONNARIES", salida);

		return lvSfvOut;
	}

	private void addFechas(String code, List<OPlyAtrS> oPlyAtrS, String fecSitu, String fecValidez) {
		OPlyAtrS ff = new OPlyAtrS();
		ff.setFldNam(FEC_COMPLETADO_IN + "_" + code);
		ff.setFldValVal(fecSitu);
		oPlyAtrS.add(ff);
		
		if (StringUtils.isNotEmpty(fecValidez)) {
			ff = new OPlyAtrS();
			ff.setFldNam(FEC_VALIDEZ_IN + "_" + code);
			ff.setFldValVal(fecValidez);
			oPlyAtrS.add(ff);
		}
	}
	
	private Pair<String, String> getFechas(String fecc) {
		if (StringUtils.isNotEmpty(fecc)) {
			if (fecc.indexOf("-") >= 0) {
	        	String[] afecc = fecc.split("-");
	        	return Pair.of(afecc[0],  afecc[1]);
	        }
	        else {
	        	return Pair.of(fecc, "");
	        }
		}
		return Pair.of("", "");
	}
	
	private boolean noHayFechaCompletado(Map<String, String> lstMap, String code) {
		String f = FEC_COMPLETADO_IN + "_" + code;
		return !(lstMap.containsKey(f) && StringUtils.isNotEmpty(lstMap.get(f)));
	}
	
	private String getSalida(OPlyAtrCPT datosPl, String varNam) {
		for (OPlyAtrP f : datosPl.getOPlyAtrPT()) {
			if (varNam.equals(f.getOPlyAtrS().getFldNam())) {
				return f.getOPlyAtrS().getFldValVal();
			}
		}
		return "";
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

}
