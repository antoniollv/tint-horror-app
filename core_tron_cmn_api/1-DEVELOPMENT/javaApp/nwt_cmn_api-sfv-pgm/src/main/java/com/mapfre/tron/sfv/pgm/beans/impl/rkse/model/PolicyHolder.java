package com.mapfre.tron.sfv.pgm.beans.impl.rkse.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyHolder {
  private String sameAsInsured;
  private String typeOfPerson;
  private String documentType;
  private String documentCode;
  private String name;
  private LocalDate birthDate;
  private Integer gender;
  private String nationality;
  private String countryOfBirth;
  private Integer maritalStatusCode;
  private String hasPublicServiceResponsibility;
  private String usesLegalRepresentative;
}