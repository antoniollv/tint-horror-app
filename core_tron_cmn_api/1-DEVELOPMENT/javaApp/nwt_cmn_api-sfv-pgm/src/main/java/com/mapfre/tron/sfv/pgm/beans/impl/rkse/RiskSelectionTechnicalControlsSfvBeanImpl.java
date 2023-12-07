package com.mapfre.tron.sfv.pgm.beans.impl.rkse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import com.mapfre.tron.api.isu.client.api.IssueApi;
import com.mapfre.tron.api.isu.client.model.InPolicy;
import com.mapfre.tron.api.isu.client.model.OPlyPlyC;
import com.mapfre.tron.api.isu.client.model.OPlyUtcP;
import com.mapfre.tron.api.isu.client.model.OPlyUtcS;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.impl.SfvWithConditionalBeanBase;
import com.mapfre.tron.sfv.pgm.beans.impl.cm.PreviousToReal;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.AppliedRule;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.RiskSelectionResponse;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Risk Selection analysis create CT using previousToReal service in quotations
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("RiskSelectionTechnicalControlsSfv")
public class RiskSelectionTechnicalControlsSfvBeanImpl extends SfvWithConditionalBeanBase {
	@Data @Builder
	private static class TC {
		private BigDecimal code;
		private String jl;
		private Set<String> msgs;
		private boolean isAudit;
	}
	
	@Autowired
	private IssueApi issueApi;
	@Autowired private PreviousToReal ptr;
	
	private static final String APL_ENR_SQN = "aplEnrSqn";
	private static final String APL_VAL = "aplVal";
	private static final String ENR_SQN = "enrSqn";
	private static final String RSK_VAL = "rskVal";
	private static final String SYS_VAL = "sysVal";
	private static final String AUTH_SYS_VAL = "authSysVal";
	private static final String RNS_SCI_VAL = "rnsSciVal";
	private static final Integer MAX_ERROR = 2000;
	private static final String TCTYPES = "TCTYPES";
	private static final String TCBORRAR = "TCBORRAR";

    @SuppressWarnings("unchecked")
	@Override
    public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal, Map<String, Object> params) {
        if (log.isInfoEnabled()) {
            log.info("Risk Selection Technical Controls - Start execution...");
        }
        
        SfvOut lvSfvOut = sfvMapper.map(in);
        String qtnVal = getString(in.getParameters().get("NUM_PRESUPUESTO"));
        List<String> tcTypes = (List<String>)params.get(TCTYPES);
        List<String> tcBorrar = (List<String>)params.get(TCBORRAR);
        
        Map<BigDecimal, TC> tcs = new HashMap<>();
        
        RiskSelectionResponse rkResponse = (RiskSelectionResponse)innerData.get("RiskSelectionResult");

        if (rkResponse == null) {
        	lvSfvOut.addMessagesItem(getMessage("Risk Selection call must be donde first", 3, 400, null));
        	
        } else if (StringUtils.isEmpty(qtnVal)) {
            lvSfvOut.addMessagesItem(getMessage("Simulation code must be informed (NUM_PRESUPUESTO)", 3, 400, null));
        
        } else {
        	// Parse technical controls information
        	parseData(rkResponse.getAppliedRules(), tcs, tcTypes);
            if (isProcess(tcBorrar, tcs)) {
	            try {
	            	// Call quotationComplete to get all data
	            	InPolicy inPolicy = new InPolicy();
	        		inPolicy.setAplEnrSqn(getI(APL_ENR_SQN, params));
	        		inPolicy.setAplVal(getI(APL_VAL, params));
	        		inPolicy.setEnrSqn(getString(params.get(ENR_SQN)));
	            	OPlyPlyC oPlyPlyC = issueApi.getQuotationInformation(inPolicy, cmpVal.intValueExact(), usrVal, lngVal, qtnVal, null);
	
	            	delTCs(tcBorrar, oPlyPlyC);
	            	
	            	processTCs(cmpVal, usrVal, params, qtnVal, tcs, inPolicy, oPlyPlyC);
		            // Call previousToReal
		            ptr.callPreviousToReal(oPlyPlyC, cmpVal, usrVal, lngVal);
			    } catch (HttpStatusCodeException e) {
			       	processApiError(lvSfvOut, false, e);
			    } catch (Exception e) {
			        String msg = String.format("Error calling issueApi: %s", e.getMessage());
			        log.error(msg);
			        lvSfvOut.addMessagesItem(getMessage(msg, 3, 400, null));
	        	}
            }
        }

        return lvSfvOut;
    }

	private boolean isProcess(List<String> tcBorrar, Map<BigDecimal, TC> tcs) {
		return isDelete(tcBorrar) || !tcs.isEmpty();
	}

	private void processTCs(final BigDecimal cmpVal, final String usrVal, Map<String, Object> params, String qtnVal,
			Map<BigDecimal, TC> tcs, InPolicy inPolicy, OPlyPlyC oPlyPlyC) {
		if (!tcs.isEmpty()) {
			// Add risk selection analysis
			// Add messages to existing TC
			completeExistingTC(tcs, oPlyPlyC);
			// New CTs
			addNewTC(cmpVal, usrVal, params, qtnVal, tcs, inPolicy, oPlyPlyC);
		}
	}

	private void delTCs(List<String> tcBorrar, OPlyPlyC oPlyPlyC) {
		if (isDelete(tcBorrar, oPlyPlyC)) {
			List<OPlyUtcP> del = new ArrayList<>();
			for (OPlyUtcP p : oPlyPlyC.getOPlyUtcPT()) {
				if (tcBorrar.contains(p.getOPlyUtcS().getErrVal().toPlainString())) {
					del.add(p);
				}
			}
			oPlyPlyC.getOPlyUtcPT().removeAll(del);
			if (oPlyPlyC.getOPlyUtcPT().isEmpty()) {
				oPlyPlyC.getOPlyGniP().getOPlyGniS().setPrvMvm("N");
			}
		}
	}

	private boolean isDelete(List<String> tcBorrar, OPlyPlyC oPlyPlyC) {
		return isDelete(tcBorrar) && oPlyPlyC.getOPlyUtcPT() != null && !oPlyPlyC.getOPlyUtcPT().isEmpty();
	}

	private boolean isDelete(List<String> tcBorrar) {
		return tcBorrar != null && !tcBorrar.isEmpty();
	}

	private void addNewTC(final BigDecimal cmpVal, final String usrVal, Map<String, Object> params, String smlVal,
			Map<BigDecimal, TC> tcs, InPolicy inPolicy, OPlyPlyC oPlyPlyC) {
		boolean isAudit = false;
		for (Map.Entry<BigDecimal, TC> newTc : tcs.entrySet()) {
			OPlyUtcS s = new OPlyUtcS();
			s.setAplEnrSqn(new BigDecimal(inPolicy.getAplEnrSqn()));
			s.setAplVal(new BigDecimal(inPolicy.getAplVal()));
			s.setCmpVal(cmpVal);
			s.setErrObsVal(newTc.getValue().getMsgs().stream().collect(Collectors.joining("\r\n")));
			if (s.getErrObsVal().length() > MAX_ERROR) {
				s.setErrObsVal(s.getErrObsVal().substring(0, MAX_ERROR));
			}
			s.setCvrVal(new BigDecimal("9999"));
			s.setEnrSqn(new BigDecimal(inPolicy.getEnrSqn()));
			s.setErrVal(newTc.getKey());
			s.setPlyVal(smlVal);
			s.setSysVal(getString(params.get(SYS_VAL)));
			s.setTccJmpLvlVal(newTc.getValue().getJl());
			s.setRskVal(new BigDecimal(getString(params.get(RSK_VAL))));
			s.setUsrVal(usrVal);
			s.setTccAtz("N");
			s.setAtzSysVal(getString(params.get(AUTH_SYS_VAL)));
			s.setRnsSciVal(new BigDecimal(getString(params.get(RNS_SCI_VAL))));
			OPlyUtcP p = new OPlyUtcP();
			p.setOPlyUtcS(s);
			oPlyPlyC.getOPlyUtcPT().add(p);
			
			isAudit = isAudit || newTc.getValue().isAudit();
		}
		
		if (isAudit) {
			oPlyPlyC.getOPlyGniP().getOPlyGniS().setPrvMvm("S");	
		}
	}
	
	private void completeExistingTCs(Map<BigDecimal, TC> tcs, List<OPlyUtcP> ltcs) {
		List<BigDecimal> toRemove = new ArrayList<>();
		for (OPlyUtcP pct : ltcs) {
			for (Map.Entry<BigDecimal, TC> newTc : tcs.entrySet()) {
				if (pct.getOPlyUtcS().getErrVal().equals(newTc.getKey())) {
					String[] msgs = pct.getOPlyUtcS().getErrObsVal().split("\r\n");
					Set<String> noDupMsgs = new HashSet<>(Arrays.asList(msgs));
					noDupMsgs.addAll(newTc.getValue().getMsgs());
					pct.getOPlyUtcS().setErrObsVal(noDupMsgs.stream().collect(Collectors.joining("\r\n")));
					if (pct.getOPlyUtcS().getErrObsVal().length() > MAX_ERROR) {
						pct.getOPlyUtcS().setErrObsVal(pct.getOPlyUtcS().getErrObsVal().substring(0, MAX_ERROR));
					}
					toRemove.add(newTc.getKey());
				}
			}
		}
		if (!toRemove.isEmpty()) {
			toRemove.stream().forEach(tcs::remove);
		}
	}

	private void completeExistingTC(Map<BigDecimal, TC> tcs, OPlyPlyC oPlyPlyC) {
		if (oPlyPlyC.getOPlyUtcPT() == null) {
    		oPlyPlyC.setOPlyUtcPT(new ArrayList<>());
    	}
		completeExistingTCs(tcs, oPlyPlyC.getOPlyUtcPT());	
	}

    private void parseData (List<AppliedRule> rules, Map<BigDecimal, TC> tcs, List<String> tcTypes) {
    	if (rules != null && !rules.isEmpty()) {
	    	rules.stream().forEach(r -> {
	    		if (r.getActions() != null && !r.getActions().isEmpty()) {
	    			r.getActions().stream().forEach(a -> parseData(r, a, tcs, tcTypes));
	    		}
	    	});
    	}
    }

    private void parseData(AppliedRule rule, Map<String, Object> action, Map<BigDecimal, TC> tcs, List<String> tcTypes) {
    	String type = Objects.toString(action.get("type"));
    	if (isTechControl(type, tcTypes)) {
    		BigDecimal ct = new BigDecimal(Objects.toString(action.get("value")));
    		String msg = getMsg(rule, action);
    		String jl = Objects.toString(action.get("jumpLevel"));
    		boolean isAudit = tcTypes.get(0).equals(type);
    		
    		if (!tcs.containsKey(ct)) {
    			TC t = TC.builder().code(ct).jl(jl).isAudit(isAudit).msgs(new HashSet<>()).build();
    			tcs.put(ct, t);
    		}
    		
    		tcs.get(ct).getMsgs().add(msg);
    	}
    }
    
    private boolean isTechControl(String type, List<String> tcTypes) {
    	return tcTypes.contains(type);
    }

    private String getMsg(AppliedRule ar, Map<String, Object> act) {
		String msg = getString(act.get("message"));
		if (StringUtils.isEmpty(msg)) {
			msg = ar.getRuleName();
		}
		return msg;
	}
    
    private Integer getI(String k, Map<String, Object> vals) {
		String val = getString(vals.get(k));
		if (StringUtils.isNotEmpty(val)) {
			try {
				return Integer.parseInt(val);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		return 0;
	}
}
