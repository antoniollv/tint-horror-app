package com.mapfre.tron.sfv.pgm.beans.impl.rkse;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.impl.SfvWithConditionalBeanBase;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.client.RiskSelectionClient;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.RiskSelectionRequest;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.RiskSelectionResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Risk Seection call.
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("RiskSelectionEngineSfv")
public class RiskSelectionEngineSfvBeanImpl extends SfvWithConditionalBeanBase {

	@Autowired
	protected RiskSelectionClient rkClient;

    @Override
    public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal, Map<String, Object> params) {
        if (log.isInfoEnabled()) {
            log.info("Risk Selection Engine - Start execution...");
        }

        SfvOut lvSfvOut = sfvMapper.map(in);
        
        RiskSelectionRequest riskSelectionRequest = (RiskSelectionRequest)innerData.get("RiskSelectionData");

        try {
        	if (params.containsKey("log")) {
        		lvSfvOut.getParameters().put("REQUEST", riskSelectionRequest);
        	}
        	
        	RiskSelectionResponse rkResponse = rkClient.postRiskSelection(riskSelectionRequest);
            lvSfvOut.getParameters().put("APPLIED_RULES", rkResponse.getAppliedRules());
            lvSfvOut.getParameters().put("FINAL_RESULT", rkResponse.getFinalResult());
            innerData.set("RiskSelectionResult", rkResponse);
        } catch (HttpStatusCodeException e) {
        	String msg = e.getResponseBodyAsString();
            log.error(msg);
            lvSfvOut.addMessagesItem(getMessage(msg, 3, e.getRawStatusCode(), null));
        } catch (Exception e) {
            String msg = String.format("Error calling RiskSelectionEngine.postRiskSelection: %s", e.getMessage());
            log.error(msg);
            lvSfvOut.addMessagesItem(getMessage(msg, 3, 400, null));
        }

        return lvSfvOut;
    }
}
