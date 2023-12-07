package com.mapfre.tron.sfv.bo;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Filter
 */
@Validated

public class Filter   {
  @JsonProperty("agnVal")
  private BigDecimal agnVal = null;

  @JsonProperty("frsLvlVal")
  private BigDecimal frsLvlVal = null;

  @JsonProperty("sncLvlval")
  private BigDecimal sncLvlval = null;

  @JsonProperty("thrLvlVal")
  private BigDecimal thrLvlVal = null;

  @JsonProperty("frsDstHnlVal")
  private String frsDstHnlVal = null;

  @JsonProperty("scnDstHnlVal")
  private String scnDstHnlVal = null;

  @JsonProperty("thrDstHnlVal")
  private String thrDstHnlVal = null;

  @JsonProperty("secVal")
  private BigDecimal secVal = null;

  @JsonProperty("sbsVal")
  private BigDecimal sbsVal = null;

  @JsonProperty("delVal")
  private Integer delVal = null;

  @JsonProperty("sblVal")
  private Integer sblVal = null;

  public Filter agnVal(BigDecimal agnVal) {
    this.agnVal = agnVal;
    return this;
  }

  /**
   * Agent
   * @return agnVal
  **/
  @ApiModelProperty(example = "0.0", value = "Agent")

  @Valid

  public BigDecimal getAgnVal() {
    return agnVal;
  }

  public void setAgnVal(BigDecimal agnVal) {
    this.agnVal = agnVal;
  }

  public Filter frsLvlVal(BigDecimal frsLvlVal) {
    this.frsLvlVal = frsLvlVal;
    return this;
  }

  /**
   * First Level
   * @return frsLvlVal
  **/
  @ApiModelProperty(example = "99.0", value = "First Level")

  @Valid

  public BigDecimal getFrsLvlVal() {
    return frsLvlVal;
  }

  public void setFrsLvlVal(BigDecimal frsLvlVal) {
    this.frsLvlVal = frsLvlVal;
  }

  public Filter sncLvlval(BigDecimal sncLvlval) {
    this.sncLvlval = sncLvlval;
    return this;
  }

  /**
   * Second Level
   * @return sncLvlval
  **/
  @ApiModelProperty(example = "999.0", value = "Second Level")

  @Valid

  public BigDecimal getSncLvlval() {
    return sncLvlval;
  }

  public void setSncLvlval(BigDecimal sncLvlval) {
    this.sncLvlval = sncLvlval;
  }

  public Filter thrLvlVal(BigDecimal thrLvlVal) {
    this.thrLvlVal = thrLvlVal;
    return this;
  }

  /**
   * Third Level
   * @return thrLvlVal
  **/
  @ApiModelProperty(example = "9999.0", value = "Third Level")

  @Valid

  public BigDecimal getThrLvlVal() {
    return thrLvlVal;
  }

  public void setThrLvlVal(BigDecimal thrLvlVal) {
    this.thrLvlVal = thrLvlVal;
  }

  public Filter frsDstHnlVal(String frsDstHnlVal) {
    this.frsDstHnlVal = frsDstHnlVal;
    return this;
  }

  /**
   * First Channel Distribution
   * @return frsDstHnlVal
  **/
  @ApiModelProperty(example = "ZZ", value = "First Channel Distribution")


  public String getFrsDstHnlVal() {
    return frsDstHnlVal;
  }

  public void setFrsDstHnlVal(String frsDstHnlVal) {
    this.frsDstHnlVal = frsDstHnlVal;
  }

  public Filter scnDstHnlVal(String scnDstHnlVal) {
    this.scnDstHnlVal = scnDstHnlVal;
    return this;
  }

  /**
   * Second Channel Distribution
   * @return scnDstHnlVal
  **/
  @ApiModelProperty(example = "ZZZ", value = "Second Channel Distribution")


  public String getScnDstHnlVal() {
    return scnDstHnlVal;
  }

  public void setScnDstHnlVal(String scnDstHnlVal) {
    this.scnDstHnlVal = scnDstHnlVal;
  }

  public Filter thrDstHnlVal(String thrDstHnlVal) {
    this.thrDstHnlVal = thrDstHnlVal;
    return this;
  }

  /**
   * Third Channel Distribution
   * @return thrDstHnlVal
  **/
  @ApiModelProperty(example = "ZZZZ", value = "Third Channel Distribution")


  public String getThrDstHnlVal() {
    return thrDstHnlVal;
  }

  public void setThrDstHnlVal(String thrDstHnlVal) {
    this.thrDstHnlVal = thrDstHnlVal;
  }

  public Filter secVal(BigDecimal secVal) {
    this.secVal = secVal;
    return this;
  }

  /**
   * Sector
   * @return secVal
  **/
  @ApiModelProperty(example = "0.0", value = "Sector")

  @Valid

  public BigDecimal getSecVal() {
    return secVal;
  }

  public void setSecVal(BigDecimal secVal) {
    this.secVal = secVal;
  }

  public Filter sbsVal(BigDecimal sbsVal) {
    this.sbsVal = sbsVal;
    return this;
  }

  /**
   * Subsector
   * @return sbsVal
  **/
  @ApiModelProperty(example = "0.0", value = "Subsector")

  @Valid

  public BigDecimal getSbsVal() {
    return sbsVal;
  }

  public void setSbsVal(BigDecimal sbsVal) {
    this.sbsVal = sbsVal;
  }

  public Filter delVal(Integer delVal) {
    this.delVal = delVal;
    return this;
  }

  /**
   * Deal value
   * @return delVal
  **/
  @ApiModelProperty(example = "99999", value = "Deal value")


  public Integer getDelVal() {
    return delVal;
  }

  public void setDelVal(Integer delVal) {
    this.delVal = delVal;
  }

  public Filter sblVal(Integer sblVal) {
    this.sblVal = sblVal;
    return this;
  }

  /**
   * Subdeal Value
   * @return sblVal
  **/
  @ApiModelProperty(example = "99999", value = "Subdeal Value")


  public Integer getSblVal() {
    return sblVal;
  }

  public void setSblVal(Integer sblVal) {
    this.sblVal = sblVal;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Filter filter = (Filter) o;
    return Objects.equals(this.agnVal, filter.agnVal) &&
        Objects.equals(this.frsLvlVal, filter.frsLvlVal) &&
        Objects.equals(this.sncLvlval, filter.sncLvlval) &&
        Objects.equals(this.thrLvlVal, filter.thrLvlVal) &&
        Objects.equals(this.frsDstHnlVal, filter.frsDstHnlVal) &&
        Objects.equals(this.scnDstHnlVal, filter.scnDstHnlVal) &&
        Objects.equals(this.thrDstHnlVal, filter.thrDstHnlVal) &&
        Objects.equals(this.secVal, filter.secVal) &&
        Objects.equals(this.sbsVal, filter.sbsVal) &&
        Objects.equals(this.delVal, filter.delVal) &&
        Objects.equals(this.sblVal, filter.sblVal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(agnVal, frsLvlVal, sncLvlval, thrLvlVal, frsDstHnlVal, scnDstHnlVal, thrDstHnlVal, secVal, sbsVal, delVal, sblVal);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Filter {\n");
    
    sb.append("    agnVal: ").append(toIndentedString(agnVal)).append("\n");
    sb.append("    frsLvlVal: ").append(toIndentedString(frsLvlVal)).append("\n");
    sb.append("    sncLvlval: ").append(toIndentedString(sncLvlval)).append("\n");
    sb.append("    thrLvlVal: ").append(toIndentedString(thrLvlVal)).append("\n");
    sb.append("    frsDstHnlVal: ").append(toIndentedString(frsDstHnlVal)).append("\n");
    sb.append("    scnDstHnlVal: ").append(toIndentedString(scnDstHnlVal)).append("\n");
    sb.append("    thrDstHnlVal: ").append(toIndentedString(thrDstHnlVal)).append("\n");
    sb.append("    secVal: ").append(toIndentedString(secVal)).append("\n");
    sb.append("    sbsVal: ").append(toIndentedString(sbsVal)).append("\n");
    sb.append("    delVal: ").append(toIndentedString(delVal)).append("\n");
    sb.append("    sblVal: ").append(toIndentedString(sblVal)).append("\n");
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

