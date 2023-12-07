package com.mapfre.tron.sfv.pgm.beans.impl.rkse.model;

import java.util.Map;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Participants {
  private PolicyHolder policyHolder;
  private PolicyHolderLegalRepresentative policyHolderLegalRepresentative;
  private Map<String, Beneficiaries> beneficiaries;
}