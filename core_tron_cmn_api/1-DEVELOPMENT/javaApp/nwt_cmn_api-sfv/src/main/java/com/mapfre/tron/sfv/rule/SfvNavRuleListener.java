package com.mapfre.tron.sfv.rule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;

import com.mapfre.tron.sfv.rule.model.NavRule;

/**
 * The Sfv navigation rule listener.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 may 2023 - 16:20:53
 *
 */
public class SfvNavRuleListener implements RuleListener {

    private SfvNavRule compilantRule;

    @Override
    public void onSuccess(Rule rule, Facts facts) {
        compilantRule = (SfvNavRule) rule;
    }

    public NavRule getCompilantRule() {
        return compilantRule.getNavRule();
    }

}
