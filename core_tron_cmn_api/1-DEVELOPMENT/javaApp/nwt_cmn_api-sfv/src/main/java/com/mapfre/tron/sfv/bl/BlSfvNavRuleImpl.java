package com.mapfre.tron.sfv.bl;

import java.util.List;
import java.util.Map;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Service;

import com.mapfre.tron.sfv.rule.SfvNavRule;
import com.mapfre.tron.sfv.rule.SfvNavRuleListener;
import com.mapfre.tron.sfv.rule.model.NavRule;

import lombok.extern.slf4j.Slf4j;

/**
 * The Sfv navigation rule business service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 may 2023 - 12:46:13
 *
 */
@Slf4j
@Service
public class BlSfvNavRuleImpl implements IBlSfvNavRule {

    @Override
    public NavRule process(final List<NavRule> navRules, final Map<String, Object> parameters) {
        if (log.isInfoEnabled()) {
            log.info("Processing navigation rules...");
        }

        // define rules
        Rules rules = this.transformIntoRules(navRules);

        // fire rules on known facts
        RulesEngineParameters ruleParameters = new RulesEngineParameters().skipOnFirstAppliedRule(true);
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine(ruleParameters);
        SfvNavRuleListener rl = new SfvNavRuleListener();
        rulesEngine.registerRuleListener(rl);
        Facts facts = this.getFacts(parameters);
        rulesEngine.fire(rules, facts);
        return rl.getCompilantRule();
    }

    private Rules transformIntoRules(final List<NavRule> navRules) {
        final Rules rules = new Rules();
        navRules.stream().forEach(myrule -> rules.register(new SfvNavRule(myrule)));

        return rules;
    }

    private Facts getFacts(final Map<String, Object> parameters) {
        Facts facts = new Facts();
        parameters.entrySet().stream().forEach(param -> {
            if (param.getKey() != null && param.getValue() != null) {
                facts.put(param.getKey(), param.getValue());
            }
        });

        return facts;
    }

}
