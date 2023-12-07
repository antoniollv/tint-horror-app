package com.mapfre.tron.sfv.pgm.beans.impl.rkse.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coverages {
  private String name;
  private BigDecimal capital;
  private Integer franchiseCode;
}