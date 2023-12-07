package com.mapfre.tron.sfv.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Output structure data
 */
@ApiModel(description = "Output structure data")
@Validated

public class SfvOut   {
  @JsonProperty("idnKey")
  private String idnKey = null;

  @JsonProperty("flwIdn")
  private String flwIdn = null;

  @JsonProperty("parameters")
  @Valid
  private Map<String, Object> parameters = null;

  @JsonProperty("messages")
  @Valid
  private List<Message> messages = null;

  @JsonProperty("navigation")
  private Navigation navigation = null;

  public SfvOut idnKey(String idnKey) {
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

  public SfvOut flwIdn(String flwIdn) {
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

  public SfvOut parameters(Map<String, Object> parameters) {
    this.parameters = parameters;
    return this;
  }

  public SfvOut putParametersItem(String key, String parametersItem) {
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

  public SfvOut messages(List<Message> messages) {
    this.messages = messages;
    return this;
  }

  public SfvOut addMessagesItem(Message messagesItem) {
    if (this.messages == null) {
      this.messages = new ArrayList<>();
    }
    this.messages.add(messagesItem);
    return this;
  }

  /**
   * Get messages
   * @return messages
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Message> getMessages() {
    return messages;
  }

  public void setMessages(List<Message> messages) {
    this.messages = messages;
  }

  public SfvOut navigation(Navigation navigation) {
    this.navigation = navigation;
    return this;
  }

  /**
   * Get navigation
   * @return navigation
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Navigation getNavigation() {
    return navigation;
  }

  public void setNavigation(Navigation navigation) {
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
    SfvOut sfvOut = (SfvOut) o;
    return Objects.equals(this.idnKey, sfvOut.idnKey) &&
        Objects.equals(this.flwIdn, sfvOut.flwIdn) &&
        Objects.equals(this.parameters, sfvOut.parameters) &&
        Objects.equals(this.messages, sfvOut.messages) &&
        Objects.equals(this.navigation, sfvOut.navigation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idnKey, flwIdn, parameters, messages, navigation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SfvOut {\n");
    
    sb.append("    idnKey: ").append(toIndentedString(idnKey)).append("\n");
    sb.append("    flwIdn: ").append(toIndentedString(flwIdn)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    messages: ").append(toIndentedString(messages)).append("\n");
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
