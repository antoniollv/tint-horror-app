package com.mapfre.tron.sfv.bo;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Navigation
 */
@Validated

public class Navigation   {
  @JsonProperty("nxtSteIdn")
  private String nxtSteIdn = null;

  @JsonProperty("pmnNvgPrrSte")
  private Boolean pmnNvgPrrSte = null;

  @JsonProperty("pmnNvgWhtPrrSte")
  private Boolean pmnNvgWhtPrrSte = null;

  public Navigation nxtSteIdn(String nxtSteIdn) {
    this.nxtSteIdn = nxtSteIdn;
    return this;
  }

  /**
   * Next Step in Navigation
   * @return nxtSteIdn
  **/
  @ApiModelProperty(example = "step2", value = "Next Step in Navigation")


  public String getNxtSteIdn() {
    return nxtSteIdn;
  }

  public void setNxtSteIdn(String nxtSteIdn) {
    this.nxtSteIdn = nxtSteIdn;
  }

  public Navigation pmnNvgPrrSte(Boolean pmnNvgPrrSte) {
    this.pmnNvgPrrSte = pmnNvgPrrSte;
    return this;
  }

  /**
   * Indicator allows you to return to the previous step
   * @return pmnNvgPrrSte
  **/
  @ApiModelProperty(example = "true", value = "Indicator allows you to return to the previous step")


  public Boolean isPmnNvgPrrSte() {
    return pmnNvgPrrSte;
  }

  public void setPmnNvgPrrSte(Boolean pmnNvgPrrSte) {
    this.pmnNvgPrrSte = pmnNvgPrrSte;
  }

  public Navigation pmnNvgWhtPrrSte(Boolean pmnNvgWhtPrrSte) {
    this.pmnNvgWhtPrrSte = pmnNvgWhtPrrSte;
    return this;
  }

  /**
   * Indicator allows you to go back any step
   * @return pmnNvgWhtPrrSte
  **/
  @ApiModelProperty(example = "false", value = "Indicator allows you to go back any step")


  public Boolean isPmnNvgWhtPrrSte() {
    return pmnNvgWhtPrrSte;
  }

  public void setPmnNvgWhtPrrSte(Boolean pmnNvgWhtPrrSte) {
    this.pmnNvgWhtPrrSte = pmnNvgWhtPrrSte;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Navigation navigation = (Navigation) o;
    return Objects.equals(this.nxtSteIdn, navigation.nxtSteIdn) &&
        Objects.equals(this.pmnNvgPrrSte, navigation.pmnNvgPrrSte) &&
        Objects.equals(this.pmnNvgWhtPrrSte, navigation.pmnNvgWhtPrrSte);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nxtSteIdn, pmnNvgPrrSte, pmnNvgWhtPrrSte);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Navigation {\n");
    
    sb.append("    nxtSteIdn: ").append(toIndentedString(nxtSteIdn)).append("\n");
    sb.append("    pmnNvgPrrSte: ").append(toIndentedString(pmnNvgPrrSte)).append("\n");
    sb.append("    pmnNvgWhtPrrSte: ").append(toIndentedString(pmnNvgWhtPrrSte)).append("\n");
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

