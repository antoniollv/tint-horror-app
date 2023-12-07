package com.mapfre.tron.sfv.bo;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Position
 */
@Validated

public class Position   {
  @JsonProperty("steIdn")
  private String steIdn = null;

  @JsonProperty("fldSet")
  private String fldSet = null;

  @JsonProperty("fldNam")
  private String fldNam = null;

  public Position steIdn(String steIdn) {
    this.steIdn = steIdn;
    return this;
  }

  /**
   * Step in the Flow
   * @return steIdn
  **/
  @ApiModelProperty(example = "step1", value = "Step in the Flow")


  public String getSteIdn() {
    return steIdn;
  }

  public void setSteIdn(String steIdn) {
    this.steIdn = steIdn;
  }

  public Position fldSet(String fldSet) {
    this.fldSet = fldSet;
    return this;
  }

  /**
   * Screen Section
   * @return fldSet
  **/
  @ApiModelProperty(example = "section1", value = "Screen Section")


  public String getFldSet() {
    return fldSet;
  }

  public void setFldSet(String fldSet) {
    this.fldSet = fldSet;
  }

  public Position fldNam(String fldNam) {
    this.fldNam = fldNam;
    return this;
  }

  /**
   * Screen Field Name
   * @return fldNam
  **/
  @ApiModelProperty(example = "thpDcmVal", value = "Screen Field Name")


  public String getFldNam() {
    return fldNam;
  }

  public void setFldNam(String fldNam) {
    this.fldNam = fldNam;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position position = (Position) o;
    return Objects.equals(this.steIdn, position.steIdn) &&
        Objects.equals(this.fldSet, position.fldSet) &&
        Objects.equals(this.fldNam, position.fldNam);
  }

  @Override
  public int hashCode() {
    return Objects.hash(steIdn, fldSet, fldNam);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Position {\n");
    
    sb.append("    steIdn: ").append(toIndentedString(steIdn)).append("\n");
    sb.append("    fldSet: ").append(toIndentedString(fldSet)).append("\n");
    sb.append("    fldNam: ").append(toIndentedString(fldNam)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

