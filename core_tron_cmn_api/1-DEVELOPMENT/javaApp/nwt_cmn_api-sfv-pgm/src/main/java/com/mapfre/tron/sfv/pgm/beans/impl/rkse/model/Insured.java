package com.mapfre.tron.sfv.pgm.beans.impl.rkse.model;

import java.util.Map;

import java.time.LocalDate;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insured {
  private Integer theInsured;
  private LocalDate birthDate;
  private Integer gender;
  private Integer occupationCode;
  private String resident;
  private String residenceCountry;
  private Integer age;
  private Integer finalNaturalAge;
  private String doesSports;
  private Integer nonFederatedSports;
  private Integer federatedSports;
  private String ridesMotorcycle;
  private Integer motorcycleCC;
  private Integer motorcycleHP;
  private String staysAbroadPlanned;
  private Map<String, StayAbroad> staysAbroad;
  private Integer maritalStatusCode;
  private Integer educationLevelCode;
  private Integer typeOfEmploymentCode;
  private String authorizesPersonalDataUse;
  private Map<String, Map<String, Object>> questionnaires;
}