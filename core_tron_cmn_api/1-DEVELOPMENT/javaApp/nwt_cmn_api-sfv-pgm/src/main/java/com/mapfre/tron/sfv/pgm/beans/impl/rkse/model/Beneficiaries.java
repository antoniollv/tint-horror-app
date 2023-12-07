package com.mapfre.tron.sfv.pgm.beans.impl.rkse.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiaries {
  private Integer participantType;
  private String beneficiaryType;
  private String isInsured;
  private String isPolicyHolder;
  private String isLegalHeir;
  private String typeOfPerson;
  private String documentType;
  private String documentCode;
  private String name;
  private LocalDate birthDate;
}