package com.mapfre.tron.gdc.sr.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Default value for a field.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DefaultValue implements Serializable {
  private static final long serialVersionUID = 5255005926134605955L;
  /** Default value. */
  private String value;
}
