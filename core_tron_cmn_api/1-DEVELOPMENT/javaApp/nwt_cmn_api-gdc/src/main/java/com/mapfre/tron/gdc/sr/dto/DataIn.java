package com.mapfre.tron.gdc.sr.dto;

import java.io.Serializable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Data for a request. */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DataIn implements Serializable  {
  private static final long serialVersionUID = 1809746731678092689L;
  /** User info. */
  private UserInfo user;
  /** User form. */
  private Map<String, String> data;
}
