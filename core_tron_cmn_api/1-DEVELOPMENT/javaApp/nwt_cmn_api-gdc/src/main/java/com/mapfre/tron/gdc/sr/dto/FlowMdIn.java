package com.mapfre.tron.gdc.sr.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data for a request.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FlowMdIn   {
  /** User. */
  private UserInfo user;
  /** Screen data. */
  private List<FlowMdValue> data;
}
