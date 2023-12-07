package com.mapfre.tron.sfv.rule;

import java.math.BigDecimal;
import java.util.Map;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.core.BasicRule;

import com.mapfre.tron.sfv.rule.model.NavRule;
import com.mapfre.tron.sfv.rule.model.NavRuleStep;

/**
 * The sfv navigation rule.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 may 2023 - 12:15:06
 *
 */

public class SfvNavRule extends BasicRule {
    private NavRule innerRule;

    public SfvNavRule(NavRule m) {
        super("Rule " + m.getOrdRulVal(), "Rule " + m.getOrdRulVal(), m.getOrdRulVal().intValue());
        this.innerRule = m;
    }

    @Override
    public boolean evaluate(Facts facts) {
        // Default rule
        if (innerRule.getRulVal() == null || innerRule.getRulVal().isEmpty()) {
            return true;
        }
        for (Map<String, NavRuleStep> item : innerRule.getRulVal()) {
            if (evaluateItem(facts, item)) {
                return true;
            }
        }
        return false;
    }

    private boolean evaluateItem(Facts facts, Map<String, NavRuleStep> item) {
        for (Map.Entry<String, NavRuleStep> step : item.entrySet()) {
            if (!evaluateStep(facts, step.getKey(), step.getValue())) {
                return false;
            }
        }
        return true;
    }

    private boolean evaluateStep(Facts facts, String fldNam, NavRuleStep step) {
        if (!facts.asMap().containsKey(fldNam)) {
            return false;
        }
        switch (step.getOp()) {
        case "=":
            return facts.get(fldNam).equals(step.getValues().get(0));
        case "!=":
            return !facts.get(fldNam).equals(step.getValues().get(0));
        case ">=":
        case ">":
        case "<":
        case "<=":
            return applyOp(facts.get(fldNam).toString(), step.getOp(), step.getValues().get(0));
        case "IN":
            return step.getValues().contains(facts.get(fldNam).toString());
        case "NOT IN":
            return !step.getValues().contains(facts.get(fldNam).toString());
        case "BETWEEN":
            return applyBetween(facts.get(fldNam).toString(), step.getValues().get(0), step.getValues().get(1));
        case "NOT BETWEEN":
            return !applyBetween(facts.get(fldNam).toString(), step.getValues().get(0), step.getValues().get(1));
        default:
            return false;
        }
    }

    private boolean applyBetween(String val1, String val21, String val22) {
        BigDecimal b1 = new BigDecimal(val1);
        BigDecimal b21 = new BigDecimal(val21);
        BigDecimal b22 = new BigDecimal(val22);
        int cmp1 = b1.compareTo(b21);
        int cmp2 = b1.compareTo(b22);
        return (cmp1 >= 0) && (cmp2 <= 0);
    }

    private boolean applyOp(String val1, String op, String val2) {
        BigDecimal b1 = new BigDecimal(val1);
        BigDecimal b2 = new BigDecimal(val2);
        int cmp = b1.compareTo(b2);
        switch (op) {
        case ">=":
            return cmp >= 0;
        case ">":
            return cmp > 0;
        case "<":
            return cmp < 0;
        case "<=":
            return cmp <= 0;
        default:
            return false;
        }
    }

    public NavRule getNavRule() {
        return this.innerRule;
    }

}
