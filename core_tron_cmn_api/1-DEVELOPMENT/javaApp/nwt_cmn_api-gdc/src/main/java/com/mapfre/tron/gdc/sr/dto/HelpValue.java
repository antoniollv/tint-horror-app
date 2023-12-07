package com.mapfre.tron.gdc.sr.dto;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Help value.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class HelpValue implements Serializable {
  private static final long serialVersionUID = -5437644151676220093L;
  /** Help value code. */
  private String code;
  /** Help value label. */
  private String label;
  /** Extended custom values */
  private Map<String, String> custom;
}
