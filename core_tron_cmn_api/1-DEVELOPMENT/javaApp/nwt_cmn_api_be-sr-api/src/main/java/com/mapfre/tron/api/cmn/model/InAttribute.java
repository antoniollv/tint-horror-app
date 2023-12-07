package com.mapfre.tron.api.cmn.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Attributes")
@Validated
public class InAttribute {
	  @JsonProperty("fldNam")
	  private String fldNam = null;

	  @JsonProperty("fldValVal")
	  private String fldValVal = null;

	  public InAttribute fldNam(String fldNam) {
	    this.fldNam = fldNam;
	    return this;
	  }

	  public String getFldNam() {
	    return fldNam;
	  }

	  public void setFldNam(String fldNam) {
	    this.fldNam = fldNam;
	  }

	  public InAttribute fldValVal(String fldValVal) {
	    this.fldValVal = fldValVal;
	    return this;
	  }

	  /**
	   * Field Value
	   * @return fldValVal
	  **/
	  @ApiModelProperty(example = "value", value = "Field Value")


	  public String getFldValVal() {
	    return fldValVal;
	  }

	  public void setFldValVal(String fldValVal) {
	    this.fldValVal = fldValVal;
	  }


	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    InAttribute inAttribute = (InAttribute) o;
	    return Objects.equals(this.fldNam, inAttribute.fldNam) &&
	        Objects.equals(this.fldValVal, inAttribute.fldValVal);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(fldNam, fldValVal);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class InAttribute {\n");
	    
	    sb.append("    fldNam: ").append(toIndentedString(fldNam)).append("\n");
	    sb.append("    fldValVal: ").append(toIndentedString(fldValVal)).append("\n");
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
