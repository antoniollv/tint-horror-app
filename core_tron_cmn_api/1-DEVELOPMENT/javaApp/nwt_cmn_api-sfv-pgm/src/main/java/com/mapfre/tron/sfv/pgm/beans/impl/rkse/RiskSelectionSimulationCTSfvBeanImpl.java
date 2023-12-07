package com.mapfre.tron.sfv.pgm.beans.impl.rkse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import com.mapfre.tron.api.isu.client.api.SimulationApi;
import com.mapfre.tron.api.isu.client.model.OPlySlpS;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.impl.SfvWithConditionalBeanBase;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.AppliedRule;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.RiskSelectionResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Risk Selection analisis of output data to save in C2000000 information instead of create CT in case of simulations.
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("RiskSelectionSimulationCTSfv")
public class RiskSelectionSimulationCTSfvBeanImpl extends SfvWithConditionalBeanBase {

	private static final int MAX_LEN_C2000000 = 80;
	
	@Autowired
	private SimulationApi simulationApi;


    @Override
    public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal, Map<String, Object> params) {
        if (log.isInfoEnabled()) {
            log.info("Risk Selection simulation CTs - Start execution...");
        }
        
        SfvOut lvSfvOut = sfvMapper.map(in);
        String smlVal = getString(in.getParameters().get("NUM_COTIZACION"));
        String lobVal = getString(params.get("COD_RAMO"));
        String tblNam = getString(params.get("TABLA"));
        RiskSelectionResponse rkResponse = (RiskSelectionResponse)innerData.get("RiskSelectionResult");

        if (StringUtils.isEmpty(smlVal)) {
            lvSfvOut.addMessagesItem(getMessage("Simulation code must be informed (NUM_COTIZACION)", 3, 400, null));
        
        } else if (rkResponse.getAppliedRules() != null && !rkResponse.getAppliedRules().isEmpty()) {
        	List<OPlySlpS> inSmlParameters = new ArrayList<>();
        	parseData(cmpVal, smlVal, tblNam, rkResponse, inSmlParameters);
        	
            try {
            	
            	simulationApi.postAddSimulationParameteres(cmpVal.intValueExact(), usrVal, lngVal, lobVal, smlVal, inSmlParameters);

	        } catch (HttpStatusCodeException e) {
	        	processApiError(lvSfvOut, false, e);
	        } catch (Exception e) {
	            String msg = String.format("Error calling simulationApi.postAddSimulationParameteres: %s", e.getMessage());
	            log.error(msg);
	            lvSfvOut.addMessagesItem(getMessage(msg, 3, 400, null));
	        }
        }

        return lvSfvOut;
    }


	private void parseData(final BigDecimal cmpVal, String smlVal, String tblNam, RiskSelectionResponse rkResponse, List<OPlySlpS> inSmlParameters) {
		for (AppliedRule ar : rkResponse.getAppliedRules()) {
			for (Map<String, Object> act : ar.getActions()) {
				String type = getString(act.get("type"));
				OPlySlpS p = new OPlySlpS();
				p.setClmNam(ar.getRuleId().toString());
				String msg = type + " - " + getMsg(ar, act);
				if (msg.length() > MAX_LEN_C2000000) {
					msg = msg.substring(0, MAX_LEN_C2000000);
				}
				p.setClmVal(msg);
				p.setCmpVal(cmpVal);
				p.setLobVal(cmpVal);
				p.setRowVal("99999");
				p.setSmnVal(smlVal);
				p.setTblNam(tblNam);
				inSmlParameters.add(p);
			}
		}
	}


	private String getMsg(AppliedRule ar, Map<String, Object> act) {
		String msg = getString(act.get("message"));
		if (StringUtils.isEmpty(msg)) {
			msg = ar.getRuleName();
		}
		return msg;
	}
}
