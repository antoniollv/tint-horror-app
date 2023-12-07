package com.mapfre.tron.api.cmn.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;
import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OTrnVrbP
 */
@Validated

public class OTrnVrbP   {
  @JsonProperty("oTrnPrcS")
  private OTrnPrcS oTrnPrcS = null;

  @JsonProperty("oTrnVrbS")
  private OTrnVrbS oTrnVrbS = null;

  public OTrnVrbP oTrnPrcS(OTrnPrcS oTrnPrcS) {
    this.oTrnPrcS = oTrnPrcS;
    return this;
  }

  /**
   * Get oTrnPrcS
   * @return oTrnPrcS
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OTrnPrcS getOTrnPrcS() {
    return oTrnPrcS;
  }

  public void setOTrnPrcS(OTrnPrcS oTrnPrcS) {
    this.oTrnPrcS = oTrnPrcS;
  }

  public OTrnVrbP oTrnVrbS(OTrnVrbS oTrnVrbS) {
    this.oTrnVrbS = oTrnVrbS;
    return this;
  }

  /**
   * Get oTrnVrbS
   * @return oTrnVrbS
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OTrnVrbS getOTrnVrbS() {
    return oTrnVrbS;
  }

  public void setOTrnVrbS(OTrnVrbS oTrnVrbS) {
    this.oTrnVrbS = oTrnVrbS;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OTrnVrbP otrnVrbP = (OTrnVrbP) o;
    return Objects.equals(this.oTrnPrcS, otrnVrbP.oTrnPrcS) &&
        Objects.equals(this.oTrnVrbS, otrnVrbP.oTrnVrbS);
  }

  @Override
  public int hashCode() {
    return Objects.hash(oTrnPrcS, oTrnVrbS);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OTrnVrbP {\n");
    
    sb.append("    oTrnPrcS: ").append(toIndentedString(oTrnPrcS)).append("\n");
    sb.append("    oTrnVrbS: ").append(toIndentedString(oTrnVrbS)).append("\n");
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

