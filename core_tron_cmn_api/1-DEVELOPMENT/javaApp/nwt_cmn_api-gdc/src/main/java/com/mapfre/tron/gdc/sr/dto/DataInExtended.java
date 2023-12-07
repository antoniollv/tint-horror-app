package com.mapfre.tron.gdc.sr.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

 
/**
 * Extended data for a concept validation request including original value.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DataInExtended {
  /** User info. */
  private UserInfo user;
  /** User form. */
  private Map<String, String> data;
  /** Original updating data. */
  private Map<String, String> dataOld;
}