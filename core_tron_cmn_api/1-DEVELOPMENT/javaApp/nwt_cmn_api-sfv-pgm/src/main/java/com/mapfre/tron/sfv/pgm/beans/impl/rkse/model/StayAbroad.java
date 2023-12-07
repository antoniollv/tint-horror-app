package com.mapfre.tron.sfv.pgm.beans.impl.rkse.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StayAbroad {
  private Integer duration;
  private Integer reason;
}