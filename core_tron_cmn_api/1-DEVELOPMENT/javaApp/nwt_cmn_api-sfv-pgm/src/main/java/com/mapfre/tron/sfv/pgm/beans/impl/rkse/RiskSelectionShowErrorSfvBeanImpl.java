package com.mapfre.tron.sfv.pgm.beans.impl.rkse;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.impl.SfvWithConditionalBeanBase;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.AppliedRule;
import com.mapfre.tron.sfv.pgm.beans.impl.rkse.model.RiskSelectionResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Risk Selection analisis of output data to show error in case of rejection.
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("RiskSelectionShowErrorSfv")
public class RiskSelectionShowErrorSfvBeanImpl extends SfvWithConditionalBeanBase {

    @Override
    public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal, Map<String, Object> params) {
        if (log.isInfoEnabled()) {
            log.info("Risk Selection show errors - Start execution...");
        }

        SfvOut lvSfvOut = sfvMapper.map(in);
        String rejection = getString(params.get("REJECTION"));
        
        RiskSelectionResponse rkResponse = (RiskSelectionResponse)innerData.get("RiskSelectionResult");

        if (rkResponse.getAppliedRules() != null && !rkResponse.getAppliedRules().isEmpty()) {
        	for (AppliedRule ar : rkResponse.getAppliedRules()) {
        		for (Map<String, Object> act : ar.getActions()) {
        			String type = getString(act.get("type"));
        			if (rejection.equals(type)) {
        				String msg = getMsg(ar, act);
        				lvSfvOut.addMessagesItem(getMessage(msg, 3, ar.getRuleId(), null));
        			}
        		}
        	}
        }

        return lvSfvOut;
    }

	private String getMsg(AppliedRule ar, Map<String, Object> act) {
		String msg = getString(act.get("message"));
		if (StringUtils.isEmpty(msg)) {
			msg = ar.getRuleName();
		}
		return msg;
	}
}
