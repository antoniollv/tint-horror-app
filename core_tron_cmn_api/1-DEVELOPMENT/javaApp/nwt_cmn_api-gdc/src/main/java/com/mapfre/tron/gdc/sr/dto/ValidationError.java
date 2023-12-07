package com.mapfre.tron.gdc.sr.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Validation error of a filed or a concept.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ValidationError implements Serializable {
  private static final long serialVersionUID = 4013038446185138467L;
  /** Error code. */
  private String code;
  /** Field code. */
  private String field;
  /** Value of the field. */
  private String value;
  /** Error condition (max value, min value, ...). */
  private String format;
  /** Value for the error condition (max value, min value, ...). */
  private String vformat;
}
