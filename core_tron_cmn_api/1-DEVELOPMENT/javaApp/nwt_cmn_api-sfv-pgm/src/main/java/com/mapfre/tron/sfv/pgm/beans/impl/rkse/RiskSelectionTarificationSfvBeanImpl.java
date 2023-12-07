package com.mapfre.tron.sfv.pgm.beans.impl.rkse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import com.mapfre.tron.api.isu.client.api.AttributeDefinitionApi;
import com.mapfre.tron.api.isu.client.api.SimulationApi;
import com.mapfre.tron.api.isu.client.model.OArdArdS;
import com.mapfre.tron.api.isu.client.model.OPlyAtrP;
import com.mapfre.tron.api.isu.client.model.OPlyAtrS;
import com.mapfre.tron.api.isu.client.model.OPlySlpS;
import com.mapfre.tron.api.isu.client.model.OutSimulationComplete;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.impl.SfvWithConditionalBeanBase;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.AppliedRule;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.RiskSelectionResponse;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Risk Selection analisis tarification and call V1 to retarification.
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("RiskSelectionTarificationSfv")
public class RiskSelectionTarificationSfvBeanImpl extends SfvWithConditionalBeanBase {
	
	private static final String MAX_ACCUM = "_MAX_ACCUM";
	@Autowired
	private SimulationApi simulationApi;
	@Autowired
	private AttributeDefinitionApi attributeDefinitionApi;
	
	@Data @Builder
	private static class CommonData {
		private BigDecimal cmpVal;
		private BigDecimal lobVal;
		private String tblNam;
		private String smlVal;
		private String tarType;
		private String mxruleid;
	}

	@SuppressWarnings("unchecked")
	@Override
    public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal, Map<String, Object> params) {
        if (log.isInfoEnabled()) {
            log.info("Risk Selection Tarification - Start execution...");
        }
        
        SfvOut lvSfvOut = sfvMapper.map(in);
        String smlVal = getString(in.getParameters().get("NUM_COTIZACION"));
        String tarType = getString(params.get("TARIFICATION"));
        String lobVal = getString(params.get("COD_RAMO"));
        String tblNam = getString(params.get("TABLA"));
        String tarea = getString(params.get("TAREA"));
        String mxruleid = getString(params.get("MAX_RULE_ID"));
        List<String> tarifParams = (List<String>)params.get("TARIF_PARAMS");

        RiskSelectionResponse rkResponse = (RiskSelectionResponse)innerData.get("RiskSelectionResult");

        if (rkResponse == null) {
        	lvSfvOut.addMessagesItem(getMessage("Risk Selection call must be donde first", 3, 400, null));
        	
        } else if (!StringUtils.isEmpty(smlVal) && tarifParams != null) {
            CommonData cd = CommonData.builder().cmpVal(cmpVal).lobVal(new BigDecimal(lobVal)).smlVal(smlVal).tblNam(tblNam)
            		.tarType(tarType).mxruleid(mxruleid).build();
            int stepId = getSteIdnId(in.getPosition().getSteIdn());

	        try {
	        	// Get all data to call retarification
	           	OutSimulationComplete outSimulationComplete = simulationApi.getSimulationComplete(cmpVal.intValueExact(), usrVal, lngVal, lobVal, smlVal);
	           	Map<String, String> values = new HashMap<>();
	           	outSimulationComplete.getOPlySlpPT().stream().forEach(x -> values.put(x.getClmNam(), x.getClmVal()));
	            	         	
		        // Parse tarification information
	        	List<OPlySlpS> inSmlParameters = new ArrayList<>();
	        	parseData(rkResponse.getAppliedRules(), cd, inSmlParameters, tarifParams, values, stepId);
	        	
	        	// Acummulate values from previous steps
	           	accumSteps(tarifParams, stepId, cd, values, inSmlParameters);
	           	
	        	// Addparametresimulation to add/update information
		        simulationApi.postAddSimulationParameteres(cmpVal.intValueExact(), usrVal, lngVal, lobVal, smlVal, inSmlParameters);

	            // take account of the parameters order 	
	           	List<OArdArdS> outOrder = attributeDefinitionApi.getAttributesDefinitionParameterTask(lngVal, cmpVal.intValueExact(), usrVal, tarea);
	           	List<OPlyAtrP> toRetarif = changeDataTarif(values, outSimulationComplete, outOrder, cmpVal, lobVal);
	    
	           	// Call tarification
	           	simulationApi.postSimulationV1(cmpVal.intValueExact(), usrVal, lngVal, lobVal, toRetarif);
	
		    } catch (HttpStatusCodeException e) {
		       	processApiError(lvSfvOut, false, e);
		    } catch (Exception e) {
		        String msg = String.format("Error calling simulationApi: %s", e.getMessage());
		        log.error(msg);
		        lvSfvOut.addMessagesItem(getMessage(msg, 3, 400, null));
		    }
        }

        return lvSfvOut;
    }

	private void accumSteps(List<String> tarifParams, int stepId, CommonData cd, Map<String, String> values, List<OPlySlpS> inSmlParameters) {
		for (String param : tarifParams) {
			Map<String,BigDecimal> accum = new HashMap<>();
			for (int i = 1; i <= stepId; i++) {
				final String idx = "_" + i;
				values.keySet().stream()
					.filter(x -> x.startsWith(param + "_") && x.endsWith(idx))
					.forEach(x -> {
						String acumParam = x.substring(0, x.length() - idx.length());
						BigDecimal val = new BigDecimal(values.get(x) != null ? values.get(x) : "0");
						if (accum.containsKey(acumParam)) {
							accum.put(acumParam, accum.get(acumParam).add(val));
						} else {
							accum.put(acumParam, val);
						}
						
					});
			}
			accum.entrySet().stream().forEach(x -> {
				values.put(x.getKey(), x.getValue().toPlainString());
				inSmlParameters.add(getSmlParam(cd, x));
			});
		}
	}
	
	private List<OPlyAtrP> changeDataTarif(Map<String, String> values, OutSimulationComplete outSimulationComplete, List<OArdArdS> order, BigDecimal cmpVal, String lobVal) {
		List<OPlyAtrP> toRetarif = new ArrayList<>();
		order.stream().forEach(x -> {
			OPlyAtrP atr = new OPlyAtrP();
			atr.setOPlyAtrS(new OPlyAtrS());
			atr.getOPlyAtrS().setFldNam(x.getFldNam());
			switch (x.getFldNam()) {
			case "COD_CIA": atr.getOPlyAtrS().setFldValVal(cmpVal.toPlainString()); break;
			case "COD_RAMO": atr.getOPlyAtrS().setFldValVal(lobVal); break;
			default: atr.getOPlyAtrS().setFldValVal(values.get(x.getFldNam())); break;
			}
			toRetarif.add(atr);
			values.remove(x.getFldNam());
		});
		
		outSimulationComplete.getOPlySlpPT().stream().forEach(x -> {
			if (values.containsKey(x.getClmNam())) {
				OPlyAtrP atr = new OPlyAtrP();
				atr.setOPlyAtrS(new OPlyAtrS());
				atr.getOPlyAtrS().setFldNam(x.getClmNam());
				atr.getOPlyAtrS().setFldValVal(x.getClmVal());
			}
		});

		return toRetarif;
	}

    private void parseData (List<AppliedRule> rules, CommonData cd, List<OPlySlpS> inSmlParameters, List<String> tarifParams, Map<String, String> values, int steIdn) {
    	Map<String, BigDecimal> retarifsInStep = new HashMap<>();
    	if (rules != null) {
	    	rules.stream().forEach(r -> {
	    		boolean maxAcc = StringUtils.isNotEmpty(cd.getMxruleid()) && r.getRuleId().toString().startsWith(cd.getMxruleid());
	    		if (r.getActions() != null && !r.getActions().isEmpty()) {
	    			r.getActions().stream()
	    				.filter(a -> isTarification(cd.getTarType(), Objects.toString(a.get("type"))))
	    				.forEach(a -> parseData(a, retarifsInStep, steIdn, maxAcc));
	    		}
	    	});
    	}
    	
    	checkMaxAccum(retarifsInStep);
    	
    	for (String param : tarifParams) {
    		values.keySet().stream()
    			.filter(x -> x.startsWith(param + "_") && (x.matches(".*\\d{4}$") || x.matches(".*\\d{4}_" + steIdn + "$")))
    			.map(x -> x.matches(".*\\d{4}_" + steIdn + "$") ? x : x + "_" + steIdn)
    			.filter(x -> !retarifsInStep.containsKey(x))
    			.forEach(x -> retarifsInStep.put(x, BigDecimal.ZERO));
       	}

    	if (!retarifsInStep.isEmpty()) {
    		for (Map.Entry<String, BigDecimal> e : retarifsInStep.entrySet()) {
				inSmlParameters.add(getSmlParam(cd, e));
				values.put(e.getKey(), e.getValue().toPlainString());
		    }
    	}
    }
    
    private void checkMaxAccum(Map<String, BigDecimal> retarifsInStep) {
    	List<Map.Entry<String, BigDecimal>> accums = retarifsInStep.entrySet().stream().filter(x -> x.getKey().endsWith(MAX_ACCUM)).collect(Collectors.toList());
    	accums.stream().forEach(x -> {
    			String key = x.getKey().substring(0, x.getKey().length() - MAX_ACCUM.length());
    			if (retarifsInStep.containsKey(key)) {
    				retarifsInStep.put(key, x.getValue().add(retarifsInStep.get(key)));
    			} else {
    				retarifsInStep.put(key, x.getValue());
    			}
    			retarifsInStep.remove(x.getKey());
    		});
    	
    }

	private OPlySlpS getSmlParam(CommonData cd, Map.Entry<String, BigDecimal> e) {
		OPlySlpS p = new OPlySlpS();
		p.setClmNam(e.getKey());
		p.setClmVal(e.getValue().toPlainString());
		p.setCmpVal(cd.getCmpVal());
		p.setLobVal(cd.getLobVal());
		p.setRowVal("99999");
		p.setSmnVal(cd.getSmlVal());
		p.setTblNam(cd.getTblNam());
		return p;
	}

    private void parseData(Map<String, Object> action, Map<String, BigDecimal> retarifsInStep, int steIdn, boolean maxAcc) {
    	String param = Objects.toString(action.get("data"));
    	String sval = action.get("value") != null ? action.get("value").toString() : "0";
    	BigDecimal value = new BigDecimal(sval);
    	String stepParam = getParamStep(param, steIdn);
    	if (maxAcc) {
    		stepParam += MAX_ACCUM;
    	}
    		
    	if (retarifsInStep.containsKey(stepParam)) {
    		if (maxAcc) { // Set max value
    			retarifsInStep.put(stepParam, value.compareTo(retarifsInStep.get(stepParam)) > 0 ? value : retarifsInStep.get(stepParam));
    		} else {
    			retarifsInStep.put(stepParam, value.add(retarifsInStep.get(stepParam)));
    		}
    	} else {
    		retarifsInStep.put(stepParam, value);
    	}
    }
    
    private String getParamStep(String param, int steIdn) {
    	return param + "_" + steIdn;
    }
    
    private boolean isTarification(String type, String tarType) {
    	return tarType.equals(type);
    }
}

