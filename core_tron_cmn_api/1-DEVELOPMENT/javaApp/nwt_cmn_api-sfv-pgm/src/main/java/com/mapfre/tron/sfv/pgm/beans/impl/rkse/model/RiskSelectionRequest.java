package com.mapfre.tron.sfv.pgm.beans.impl.rkse.model;

import java.util.Map;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskSelectionRequest {
  private String languageId;
  private Integer companyId;
  private Integer branchId;
  private Integer productId;
  private String executionType;
  private Integer processTypeId;
  private Integer processStep;
  private Long operationNumber;
  private Long operationDate;
  private Map<String, Object> fixedData;
  private Insured insured;
  private Map<Integer, Coverages> coverages;
  private Map<String, Object> variableData;
  private Participants participants;
}