package com.mapfre.tron.sfv.bo;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Message
 */
@Validated

public class Message   {
  @JsonProperty("msgTypVal")
  private Integer msgTypVal = null;

  @JsonProperty("msgVal")
  private Integer msgVal = null;

  @JsonProperty("msgTxtVal")
  private String msgTxtVal = null;

  @JsonProperty("fldNam")
  private String fldNam = null;

  public Message msgTypVal(Integer msgTypVal) {
    this.msgTypVal = msgTypVal;
    return this;
  }

  /**
   * Message Type
   * @return msgTypVal
  **/
  @ApiModelProperty(example = "0-error, 1-info, 2-success", value = "Message Type")


  public Integer getMsgTypVal() {
    return msgTypVal;
  }

  public void setMsgTypVal(Integer msgTypVal) {
    this.msgTypVal = msgTypVal;
  }

  public Message msgVal(Integer msgVal) {
    this.msgVal = msgVal;
    return this;
  }

  /**
   * Message Code
   * @return msgVal
  **/
  @ApiModelProperty(example = "20001", value = "Message Code")


  public Integer getMsgVal() {
    return msgVal;
  }

  public void setMsgVal(Integer msgVal) {
    this.msgVal = msgVal;
  }

  public Message msgTxtVal(String msgTxtVal) {
    this.msgTxtVal = msgTxtVal;
    return this;
  }

  /**
   * Message Text
   * @return msgTxtVal
  **/
  @ApiModelProperty(example = "NO EXITS", value = "Message Text")


  public String getMsgTxtVal() {
    return msgTxtVal;
  }

  public void setMsgTxtVal(String msgTxtVal) {
    this.msgTxtVal = msgTxtVal;
  }

  public Message fldNam(String fldNam) {
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
    Message message = (Message) o;
    return Objects.equals(this.msgTypVal, message.msgTypVal) &&
        Objects.equals(this.msgVal, message.msgVal) &&
        Objects.equals(this.msgTxtVal, message.msgTxtVal) &&
        Objects.equals(this.fldNam, message.fldNam);
  }

  @Override
  public int hashCode() {
    return Objects.hash(msgTypVal, msgVal, msgTxtVal, fldNam);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Message {\n");
    
    sb.append("    msgTypVal: ").append(toIndentedString(msgTypVal)).append("\n");
    sb.append("    msgVal: ").append(toIndentedString(msgVal)).append("\n");
    sb.append("    msgTxtVal: ").append(toIndentedString(msgTxtVal)).append("\n");
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

