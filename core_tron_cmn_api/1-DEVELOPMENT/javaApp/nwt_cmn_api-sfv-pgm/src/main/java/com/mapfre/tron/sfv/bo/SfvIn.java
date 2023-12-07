package com.mapfre.tron.sfv.bo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Input structure data (agent, channel ...)
 */
@ApiModel(description = "Input structure data (agent, channel ...)")
@Validated

public class SfvIn   {
  @JsonProperty("idnKey")
  private String idnKey = null;

  @JsonProperty("flwIdn")
  private String flwIdn = null;

  @JsonProperty("filter")
  private Filter filter = null;

  @JsonProperty("position")
  private Position position = null;

  @JsonProperty("parameters")
  @Valid
  private Map<String, Object> parameters = null;

  @JsonProperty("navigation")
  private Boolean navigation = null;

  public SfvIn idnKey(String idnKey) {
    this.idnKey = idnKey;
    return this;
  }

  /**
   * Key identifier
   * @return idnKey
  **/
  @ApiModelProperty(value = "Key identifier")


  public String getIdnKey() {
    return idnKey;
  }

  public void setIdnKey(String idnKey) {
    this.idnKey = idnKey;
  }

  public SfvIn flwIdn(String flwIdn) {
    this.flwIdn = flwIdn;
    return this;
  }

  /**
   * Operation
   * @return flwIdn
  **/
  @ApiModelProperty(example = "smn", value = "Operation")


  public String getFlwIdn() {
    return flwIdn;
  }

  public void setFlwIdn(String flwIdn) {
    this.flwIdn = flwIdn;
  }

  public SfvIn filter(Filter filter) {
    this.filter = filter;
    return this;
  }

  /**
   * Get filter
   * @return filter
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Filter getFilter() {
    return filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }

  public SfvIn position(Position position) {
    this.position = position;
    return this;
  }

  /**
   * Get position
   * @return position
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public SfvIn parameters(Map<String, Object> parameters) {
    this.parameters = parameters;
    return this;
  }

  public SfvIn putParametersItem(String key, String parametersItem) {
    if (this.parameters == null) {
      this.parameters = new HashMap<>();
    }
    this.parameters.put(key, parametersItem);
    return this;
  }

  /**
   * json screen data map
   * @return parameters
  **/
  @ApiModelProperty(value = "json screen data map")


  public Map<String, Object> getParameters() {
    return parameters;
  }

  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }

  public SfvIn navigation(Boolean navigation) {
    this.navigation = navigation;
    return this;
  }

  /**
   * navigation indicator
   * @return navigation
  **/
  @ApiModelProperty(example = "true", value = "navigation indicator")


  public Boolean isNavigation() {
    return navigation;
  }

  public void setNavigation(Boolean navigation) {
    this.navigation = navigation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SfvIn sfvIn = (SfvIn) o;
    return Objects.equals(this.idnKey, sfvIn.idnKey) &&
        Objects.equals(this.flwIdn, sfvIn.flwIdn) &&
        Objects.equals(this.filter, sfvIn.filter) &&
        Objects.equals(this.position, sfvIn.position) &&
        Objects.equals(this.parameters, sfvIn.parameters) &&
        Objects.equals(this.navigation, sfvIn.navigation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idnKey, flwIdn, filter, position, parameters, navigation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SfvIn {\n");
    
    sb.append("    idnKey: ").append(toIndentedString(idnKey)).append("\n");
    sb.append("    flwIdn: ").append(toIndentedString(flwIdn)).append("\n");
    sb.append("    filter: ").append(toIndentedString(filter)).append("\n");
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    navigation: ").append(toIndentedString(navigation)).append("\n");
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

