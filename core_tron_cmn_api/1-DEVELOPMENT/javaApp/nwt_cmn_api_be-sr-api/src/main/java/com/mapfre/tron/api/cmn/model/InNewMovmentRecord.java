package com.mapfre.tron.api.cmn.model;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;
import com.mapfre.nwt.trn.thp.tmc.bo.OThpTmcS;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Input movement record
 */
@ApiModel(description = "Input movement record")
@Validated

public class InNewMovmentRecord   {
  @JsonProperty("CmnMvrS")
  private OCmnMvrS cmnMvrS = null;

  @JsonProperty("ThpTmcS")
  private OThpTmcS thpTmcS = null;

  public InNewMovmentRecord cmnMvrS(OCmnMvrS cmnMvrS) {
    this.cmnMvrS = cmnMvrS;
    return this;
  }

  /**
   * Get cmnMvrS
   * @return cmnMvrS
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OCmnMvrS getCmnMvrS() {
    return cmnMvrS;
  }

  public void setCmnMvrS(OCmnMvrS cmnMvrS) {
    this.cmnMvrS = cmnMvrS;
  }

  public InNewMovmentRecord thpTmcS(OThpTmcS thpTmcS) {
    this.thpTmcS = thpTmcS;
    return this;
  }

  /**
   * Get ocmnTmrS
   * @return ocmnTmrS
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OThpTmcS getThpTmcS() {
    return thpTmcS;
  }

  public void setThpTmcP(OThpTmcS thpTmcS) {
    this.thpTmcS = thpTmcS;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InNewMovmentRecord inNewMovmentRecord = (InNewMovmentRecord) o;
    return Objects.equals(this.cmnMvrS, inNewMovmentRecord.cmnMvrS) &&
        Objects.equals(this.thpTmcS, inNewMovmentRecord.thpTmcS);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cmnMvrS, thpTmcS);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InNewMovmentRecord {\n");
    
    sb.append("    cmnMvrS: ").append(toIndentedString(cmnMvrS)).append("\n");
    sb.append("    thpTmcS: ").append(toIndentedString(thpTmcS)).append("\n");
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

