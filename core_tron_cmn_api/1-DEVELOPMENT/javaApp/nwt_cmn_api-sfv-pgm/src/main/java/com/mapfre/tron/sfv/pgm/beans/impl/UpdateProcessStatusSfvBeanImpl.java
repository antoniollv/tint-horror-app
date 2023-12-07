package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.OPlySmnDocQryS;
import com.mapfre.tron.sfv.bo.PlyDocument;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.impl.cm.DocumentsComponent;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.AppliedRule;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.RiskSelectionResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("UpdateProcessStatusSfv")
public class UpdateProcessStatusSfvBeanImpl extends SfvWithConditionalBeanBase {
	
	private static final String NUM_COTIZACION = "NUM_COTIZACION";
	private static final String NUM_POLIZA = "NUM_POLIZA";
	private static final String NUM_PRESUPUESTO = "NUM_PRESUPUESTO";

	@Autowired
    protected ISrCmnSfvVld iSrCmnSfvVld;
	
	@Autowired private DocumentsComponent docsQry;

	@Override
	protected SfvOut logic(SfvIn in, BigDecimal cmpVal, String usrVal, String lngVal, Map<String, Object> params) {
		if (log.isInfoEnabled()) {
			log.info("UpdateProcessStatusSfv.execute start execute operation...");
		}
		
		SfvOut lvSfvOut = sfvMapper.map(in);

		BigDecimal tipMtoBatch = getBD(params.get("TIP_MVTO_BATCH"));
		String pl =  getString(params.get("PGM_NAM"));
		String rejection = getString(params.get("REJECTION"));
		String audit = getString(params.get("AUDIT"));
		String docs = getString(params.get("DOCS"));
		String idStep = "" + getSteIdnId(in.getPosition().getSteIdn());
		
		if (StringUtils.isNotEmpty(pl)) {

			RiskSelectionResponse rkResponse = (RiskSelectionResponse)innerData.get("RiskSelectionResult");
			
			List<OPlyAtrP> inAtrs = new ArrayList<>();
			OPlyAtrS inAtr = new OPlyAtrS();
			inAtr.setCmpVal(cmpVal);
			inAtr.setQtnVal(getString(in.getParameters().get(NUM_PRESUPUESTO)));
			inAtr.setPlyVal(getString(in.getParameters().get(NUM_POLIZA)));
			inAtr.setSmnVal(getString(in.getParameters().get(NUM_COTIZACION)));
			inAtr.setMdtVal(getBD(params.get("COD_MODALIDAD")));
			inAtr.setLobVal(getBD(params.get("COD_RAMO")));
			inAtr.setEnrSqn(getBD(params.get("NUM_SPTO")));
			inAtr.setAplEnrSqn(getBD(params.get("NUM_APLI_SPTO")));
			inAtr.setAplVal(getBD(params.get("NUM_APLI")));
			inAtr.setRskVal(getBD(params.get("NUM_RIESGO")));
			inAtr.setUsrVal(usrVal);
			inAtrs.add(getSteIdn(inAtr, in));
			inAtrs.add(getFldNam(in));
			inAtrs.add(getScrSci(in));
			inAtrs.add(getRskSts(rkResponse, rejection, audit));
			inAtrs.add(getLvlTypVal(params));
			inAtrs.add(getPedVal(params));
			inAtrs.add(getRpoPymTypVal());
			
			Pair<Integer, Integer> docsData = getDocsStatus(cmpVal, usrVal, in, idStep, docs);
			inAtrs.add(getSDocs(docsData.getLeft()));
			inAtrs.add(getRDocs(docsData.getRight()));
			
			try {
				OPlyAtrCPT datosPl = iSrCmnSfvVld.vldSts(inAtrs, tipMtoBatch, pl);
				NwtUtils lvNwtUtils = new NwtUtils();
		        lvNwtUtils.isTrmOk(datosPl, "iSrCmnSfvVld.vldSts");
		    } catch (Exception e) {
				processException(lvSfvOut, e);
			}
		}
		
		return lvSfvOut;
	}

	private Pair<Integer, Integer> getDocsStatus(BigDecimal cmpVal, String usrVal, SfvIn in, String idStep, String sdocs) {
		if ("S".equals(sdocs)) {
			List<OPlySmnDocQryS> docs = docsQry.getDocsByStep(cmpVal, new BigDecimal("999"), idStep);
			if (!docs.isEmpty()) {
			
				String plyVal = getPlyVal(in);
				boolean isPly = isPly(in);
				
				List<PlyDocument> plyDocs = null;
				if (isPly) {
					plyDocs = docsQry.getDocsByPolicy(cmpVal, usrVal, plyVal);
				} else {
					plyDocs = docsQry.getDocsByQuotation(cmpVal, usrVal, plyVal, 0);
				}
				
				Set<String> avlDocs = docsQry.getDocNames(plyDocs);
				
				List<OPlySmnDocQryS> uDocs = docsQry.getUnsignedDocs(docs, avlDocs);
				
				List<OPlySmnDocQryS> pDocs = docsQry.getPendingDocs(docs, avlDocs);
				
				return Pair.<Integer, Integer>of(uDocs.size(), pDocs.size());
			}
		}
		return Pair.<Integer, Integer>of(0, 0);
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
	
	private boolean isPly(SfvIn in) {
		boolean isPly = false;
		String plyVal = getString(in.getParameters().get(NUM_POLIZA));
		if (StringUtils.isNotEmpty(plyVal)) {
			isPly = true;
		}
		return isPly;
	}

	private OPlyAtrP getSteIdn(OPlyAtrS inAtr, SfvIn in) {
		inAtr.setFldNam("steIdn");
		inAtr.setFldValVal(in.getPosition().getSteIdn());
		return getOPlyAtrP(inAtr);
	}

	private OPlyAtrP getOPlyAtrP(OPlyAtrS inAtr) {
		OPlyAtrP p = new OPlyAtrP();
		p.setOPlyAtrS(inAtr);
		return p;
	}

	private OPlyAtrP getFldNam(SfvIn in) {
		OPlyAtrS inAtr = new OPlyAtrS();
		inAtr.setFldNam("fldNam");
		inAtr.setFldValVal(in.getPosition().getFldNam());
		return getOPlyAtrP(inAtr);
	}

	private OPlyAtrP getScrSci(SfvIn in) {
		OPlyAtrS inAtr = new OPlyAtrS();
		inAtr.setFldNam("scrSci");
		inAtr.setFldValVal(in.getPosition().getFldSet());
		return getOPlyAtrP(inAtr);
	}
	
	private OPlyAtrP getRpoPymTypVal() {
		OPlyAtrS inAtr = new OPlyAtrS();
		inAtr.setFldNam("rpoPymTypVal");
		inAtr.setFldValVal("S");
		return getOPlyAtrP(inAtr);
	}

	private OPlyAtrP getRskSts(RiskSelectionResponse rkResponse, String rejection, String audit) {
		OPlyAtrS inAtr = new OPlyAtrS();
		inAtr.setFldNam("rskSts");
		boolean brejection = false;
		boolean baudit = false;
		if (rkResponse != null && rkResponse.getAppliedRules() != null && !rkResponse.getAppliedRules().isEmpty()) {
			for (AppliedRule ar : rkResponse.getAppliedRules()) {
				for (Map<String, Object> act : ar.getActions()) {
					String type = getString(act.get("type"));
					brejection = rejection.equals(type);
					baudit = audit.equals(type);
					if (brejection) {
						break;
					}
				}
				if (brejection) {
					break;
				}
			}
		}
		setVal(rejection, audit, inAtr, brejection, baudit);
		return getOPlyAtrP(inAtr);
	}

	private void setVal(String rejection, String audit, OPlyAtrS inAtr, boolean brejection, boolean baudit) {
		if (brejection) {
			inAtr.setFldValVal(rejection);
		} else if (baudit) {
			inAtr.setFldValVal(audit);
		}
	}

	private OPlyAtrP getLvlTypVal(Map<String, Object> params) {
		OPlyAtrS inAtr = new OPlyAtrS();
		inAtr.setFldNam("lvlTypVal");
		inAtr.setFldValVal(getString(params.get("TIP_NIVEL")));
		return getOPlyAtrP(inAtr);
	}

	private OPlyAtrP getPedVal(Map<String, Object> params) {
		OPlyAtrS inAtr = new OPlyAtrS();
		inAtr.setFldNam("pedVal");
		inAtr.setFldValVal(getString(params.get("NUM_PERIODO")));
		return getOPlyAtrP(inAtr);
	}

	private OPlyAtrP getSDocs(Integer sdocs) {
		OPlyAtrS inAtr = new OPlyAtrS();
		inAtr.setFldNam("sDocs");
		inAtr.setFldValVal(sdocs.toString());
		return getOPlyAtrP(inAtr);
	}

	private OPlyAtrP getRDocs(Integer rdocs) {
		OPlyAtrS inAtr = new OPlyAtrS();
		inAtr.setFldNam("rDocs");
		inAtr.setFldValVal(rdocs.toString());
		return getOPlyAtrP(inAtr);
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
