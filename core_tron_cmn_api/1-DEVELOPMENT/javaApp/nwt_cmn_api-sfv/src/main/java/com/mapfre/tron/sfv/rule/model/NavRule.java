package com.mapfre.tron.sfv.rule.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The nav rule model class.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 24 may 2023 - 16:51:43
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NavRule {

    private List<Map<String, NavRuleStep>> rulVal;
    private String nxtSteIdn;
    private String pmnNvgPrrSte;
    private String pmnNvgWhtPrrSte;
    private BigDecimal ordRulVal;

}
