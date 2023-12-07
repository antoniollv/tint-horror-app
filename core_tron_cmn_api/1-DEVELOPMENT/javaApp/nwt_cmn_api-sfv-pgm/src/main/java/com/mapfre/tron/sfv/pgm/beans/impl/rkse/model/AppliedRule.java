package com.mapfre.tron.sfv.pgm.beans.impl.rkse.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppliedRule {
    private Integer ruleId;
    private String ruleName;
    private List<Map<String, Object>> ruleConditions;
    private String productId;
    private Integer coverageId;
    private String processTypeId;
    private String processStep;
    private List<Map<String, Object>> actions;
    private String visible;
    private String visibilityRole;
    private List<FactorValue> factorValues;
}