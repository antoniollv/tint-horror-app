package com.mapfre.tron.sfv.pgm.beans.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import com.mapfre.tron.api.isu.client.api.IssueApi;
import com.mapfre.tron.api.isu.client.model.InNewCompleteIssue1;
import com.mapfre.tron.api.isu.client.model.InPolicy;
import com.mapfre.tron.api.isu.client.model.OPlyGniS;
import com.mapfre.tron.api.isu.client.model.OPlyInaP;
import com.mapfre.tron.api.isu.client.model.OPlyMpgS;
import com.mapfre.tron.api.isu.client.model.OPlyPlyC;
import com.mapfre.tron.api.thp.client.api.CommissionChartApi;
import com.mapfre.tron.api.thp.client.model.OThpCmcS;
import com.mapfre.tron.sfv.bo.Message;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;
import com.mapfre.tron.sfv.pgm.beans.impl.cm.PreviousToReal;

import lombok.extern.slf4j.Slf4j;

/**
 * The ToPaymentPlatformTokenSfvBeanImpl SfvBean implementation.
 * Prepare data to payment platform for token
 *
 * @author VIDA
 * @since 1.8
 * @version 12 May 2023 - 09:33:01
 *
 */
@Slf4j
@Component("CreatePolicySfv")
public class CreatePolicySfvBeanImpl extends SfvWithConditionalBeanBase {

    private static final String NUM_POLIZA = "NUM_POLIZA";
    private static final String PRV_MVM = "PRV_MVM";
    private static final String NUM_PRESUPUESTO = "NUM_PRESUPUESTO";
    
    private static final String APL_ENR_SQN = "aplEnrSqn";
    private static final String APL_VAL = "aplVal";
    private static final String ENR_SQN = "enrSqn";
    
    @Autowired private IssueApi issueApi;
    @Autowired private PreviousToReal ptr;
    @Autowired private CommissionChartApi commissionChartApi;
    

    @Override
    public SfvOut logic(final SfvIn in, final BigDecimal cmpVal, final String usrVal, final String lngVal,
            Map<String, Object> params) {
        if (log.isInfoEnabled()) {
            log.info("CreatePolicySfvBeanImpl.execute start execute operation...");
        }

        SfvOut lvSfvOut = sfvMapper.map(in);

        String qtnVal = getString(in.getParameters().get(NUM_PRESUPUESTO));
        if (StringUtils.isEmpty(qtnVal)) {
            Message lvMessagesItem = getMessage(NUM_PRESUPUESTO, 3, 20000, null);
            lvSfvOut.addMessagesItem(lvMessagesItem);
        } else {
    
            try {
                lvSfvOut.getParameters().put(PRV_MVM, "N");
                InPolicy inPolicy = new InPolicy();
                inPolicy.setAplEnrSqn(getI(APL_ENR_SQN, params));
                inPolicy.setAplVal(getI(APL_VAL, params));
                inPolicy.setEnrSqn(getString(params.get(ENR_SQN)));
                
                if (params.containsKey("PRESUPUESTO")) {
                    OPlyGniS g1 = generateQtn(inPolicy, cmpVal, usrVal, lngVal, qtnVal, params.containsKey("log"), lvSfvOut);
                    lvSfvOut.getParameters().put("NEW_" + NUM_PRESUPUESTO, g1.getQtnVal());
                    lvSfvOut.getParameters().put(PRV_MVM, g1.getPrvMvm());
                } else if (params.containsKey("POLIZA")) {
                    OPlyGniS g1 = generatePly(inPolicy, cmpVal, usrVal, lngVal, qtnVal, params.containsKey("log"), lvSfvOut);
                    lvSfvOut.getParameters().put(NUM_POLIZA, g1.getPlyVal());
                    lvSfvOut.getParameters().put(PRV_MVM, g1.getPrvMvm());
                } else {
                    OPlyGniS g1 = generateQtn(inPolicy, cmpVal, usrVal, lngVal, qtnVal, params.containsKey("log"), lvSfvOut);
                    lvSfvOut.getParameters().put("NEW_" + NUM_PRESUPUESTO, g1.getQtnVal());
                    lvSfvOut.getParameters().put(PRV_MVM, g1.getPrvMvm());
                    if (!"S".equals(g1.getPrvMvm())) {
                        g1 = generatePly(inPolicy, cmpVal, usrVal, lngVal, qtnVal, params.containsKey("log"), lvSfvOut);
                        lvSfvOut.getParameters().put(NUM_POLIZA, g1.getPlyVal());
                        lvSfvOut.getParameters().put(PRV_MVM, g1.getPrvMvm());
                    }
                }
            } catch (HttpStatusCodeException e) {
                processApiError(lvSfvOut, false, e);
            } catch (Exception e) {
                String msg = String.format("Error calling issueApi: %s", e.getMessage());
                log.error(msg);
                lvSfvOut.addMessagesItem(getMessage(msg, 3, 400, null));
            }
        }

        return lvSfvOut;
    }
        
    private OPlyGniS generateQtn(InPolicy inPolicy, BigDecimal cmpVal, String usrVal, String lngVal, String qtnVal, boolean log, SfvOut lvSfvOut) {
        OPlyPlyC oPlyPlyC = issueApi.getQuotationInformation(inPolicy, cmpVal.intValueExact(), usrVal, lngVal, qtnVal, null);
        
        
        completeCommisionChart(oPlyPlyC, cmpVal, usrVal, lngVal);
        
        // Generate new quotation
        InNewCompleteIssue1 o1 = new InNewCompleteIssue1();
        o1.setOPlyPlyC(oPlyPlyC);
        o1.setOPlyMpgS(getHeader(oPlyPlyC, "8"));
        if (log) {
    		lvSfvOut.getParameters().put("REQUEST", o1);
    	}
        return ptr.callQuotationQuotation2(o1, cmpVal, usrVal, lngVal);
    }
    
    private void completeCommisionChart(OPlyPlyC oPlyPlyC, BigDecimal cmpVal, String usrVal, String lngVal) {
    	Optional<OPlyInaP> agn = oPlyPlyC.getOPlyInaPT().stream().filter(x -> "P".equals(x.getOPlyInaS().getItcVal())).findFirst();
    	if (agn.isPresent()) {
    		List<OThpCmcS> ch = commissionChartApi.getCommissionChartByAgn(cmpVal.intValueExact(), usrVal, lngVal, agn.get().getOPlyInaS().getThpVal().intValueExact(), "V", null);
    		BigDecimal cmcVal = ch.get(0).getCmcVal();
    		agn.get().getOPlyInaS().cmcVal(cmcVal);
    	}
    }
    
    private OPlyGniS generatePly(InPolicy inPolicy, BigDecimal cmpVal, String usrVal, String lngVal, String qtnVal, boolean log, SfvOut lvSfvOut) {
        OPlyPlyC oPlyPlyC = issueApi.getQuotationInformation(inPolicy, cmpVal.intValueExact(), usrVal, lngVal, qtnVal, null);
        
        // Generate new quotation
        InNewCompleteIssue1 o1 = new InNewCompleteIssue1();
        o1.setOPlyPlyC(oPlyPlyC);
        o1.setOPlyMpgS(getHeader(oPlyPlyC, "3"));
        if (log) {
    		lvSfvOut.getParameters().put("REQUEST", o1);
    	}
        return ptr.callPolicyQuotation2(o1, cmpVal, usrVal, lngVal);
    }
    
    private OPlyMpgS getHeader(OPlyPlyC oPlyPlyC, String tpp) {
        OPlyMpgS h = new OPlyMpgS();
        h.cmpVal(oPlyPlyC.getOPlyGniP().getOPlyGniS().getCmpVal());
        h.lobVal(oPlyPlyC.getOPlyGniP().getOPlyGniS().getLobVal());
        h.enrSqn(oPlyPlyC.getOPlyGniP().getOPlyGniS().getEnrSqn());
        h.aplVal(oPlyPlyC.getOPlyGniP().getOPlyGniS().getAplVal());
        h.aplEnrSqn(oPlyPlyC.getOPlyGniP().getOPlyGniS().getAplEnrSqn());
        h.enrEfcDat(oPlyPlyC.getOPlyGniP().getOPlyGniS().getEnrEfcDat());
        h.enrExpDat(oPlyPlyC.getOPlyGniP().getOPlyGniS().getEnrExpDat());
        h.mdfDat(oPlyPlyC.getOPlyGniP().getOPlyGniS().getIsuDat());
        h.pocDat(oPlyPlyC.getOPlyGniP().getOPlyGniS().getIsuDat());
        h.usrVal(oPlyPlyC.getOPlyGniP().getOPlyGniS().getUsrVal());
        h.cptUsrVal(oPlyPlyC.getOPlyGniP().getOPlyGniS().getUsrVal());
        h.plyVal(oPlyPlyC.getOPlyGniP().getOPlyGniS().getPlyVal());
        h.qtnVal(oPlyPlyC.getOPlyGniP().getOPlyGniS().getQtnVal());
        h.tnrPlyTypVal("F");
        h.btcMvmTypVal(tpp);
        h.secVal(oPlyPlyC.getOPlyGniP().getOPlyGniS().getSecVal());
        if (oPlyPlyC.getOPlyInaPT() != null && !oPlyPlyC.getOPlyInaPT().isEmpty()) {
            h.frsLvlVal(oPlyPlyC.getOPlyInaPT().get(0).getOPlyInaS().getFrsLvlVal());
            h.scnLvlVal(oPlyPlyC.getOPlyInaPT().get(0).getOPlyInaS().getScnLvlVal());
            h.thrLvlVal(oPlyPlyC.getOPlyInaPT().get(0).getOPlyInaS().getThrLvlVal());
            h.thpVal(oPlyPlyC.getOPlyInaPT().get(0).getOPlyInaS().getThpVal());
        }
        h.crnVal(oPlyPlyC.getOPlyGniP().getOPlyGniS().getCrnVal());
        h.mnlPre("N");
        h.stsTypVal("1");
        h.plyRnw("N");
        h.plyCanDbt("N");
        return h;
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