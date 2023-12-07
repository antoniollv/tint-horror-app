package com.mapfre.tron.gdc.sr.dto;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Value for a concept in a master-detail flow.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FlowMdValue   {
  /** Concept ID. */
  private String idConcept;
  /** Data. */
  private List<Map<String, String>> data;
}
