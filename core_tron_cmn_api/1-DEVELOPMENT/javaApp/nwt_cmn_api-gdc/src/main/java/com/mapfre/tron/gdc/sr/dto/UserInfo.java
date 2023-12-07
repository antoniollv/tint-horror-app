package com.mapfre.tron.gdc.sr.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Data of the user. */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserInfo implements Serializable {
  private static final long serialVersionUID = 5703550020853353048L;
  /** User id. */
  private String id;
  /** User company. */
  private Integer company;
  /** User idiom. */
  private String language;
}
