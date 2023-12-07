package com.mapfre.tron.sfv.pgm.beans.impl.rkse.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyHolderLegalRepresentative {
  private String documentType;
  private String documentCode;
  private String name;
  private LocalDate birthDate;
  private Integer gender;
  private String nationality;
  private String countryOfResidence;
  private String hasPublicServiceResponsibility;
  private Integer maritalStatusCode;
  private String typeOfPerson;
}