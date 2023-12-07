package com.mapfre.tron.sfv.pgm.beans.impl.rkse.model;

import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskSelectionResponse {
  private String requestId;
  private Integer companyId;
  private Integer productId;
  private String executionType;
  private Integer processTypeId;
  private Integer processStep;
  private Integer operationNumber;
  private Long operationDate;
  private RiskSelectionRequest request;
  private List<AppliedRule> appliedRules;
  private List<Object> finalResult;
}