package com.mapfre.tron.api.cmn.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * S if the company is new third party model, otherwise N
 */
@ApiModel(description = "S if the company is new third party model, otherwise N")
@Validated

public class McaMdtThpNewTrueReponsenDefinition   {
  @JsonProperty("McaMdtThpNew")
  private String mcaMdtThpNew = null;

  public McaMdtThpNewTrueReponsenDefinition mcaMdtThpNew(String mcaMdtThpNew) {
    this.mcaMdtThpNew = mcaMdtThpNew;
    return this;
  }

  /**
   * Get mcaMdtThpNew
   * @return mcaMdtThpNew
  **/
  @ApiModelProperty(value = "")


  public String getMcaMdtThpNew() {
    return mcaMdtThpNew;
  }

  public void setMcaMdtThpNew(String mcaMdtThpNew) {
    this.mcaMdtThpNew = mcaMdtThpNew;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    McaMdtThpNewTrueReponsenDefinition mcaMdtThpNewTrueReponsenDefinition = (McaMdtThpNewTrueReponsenDefinition) o;
    return Objects.equals(this.mcaMdtThpNew, mcaMdtThpNewTrueReponsenDefinition.mcaMdtThpNew);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mcaMdtThpNew);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class McaMdtThpNewTrueReponsenDefinition {\n");
    
    sb.append("    mcaMdtThpNew: ").append(toIndentedString(mcaMdtThpNew)).append("\n");
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

