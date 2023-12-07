package com.mapfre.tron.sfv.bl;

import java.util.List;
import java.util.Map;

import com.mapfre.tron.sfv.rule.model.NavRule;

/**
 * The Sfv navigation rule business service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 may 2023 - 12:25:20
 *
 */
public interface IBlSfvNavRule {

    NavRule process(List<NavRule> navRules, Map<String, Object> parameters);

}
