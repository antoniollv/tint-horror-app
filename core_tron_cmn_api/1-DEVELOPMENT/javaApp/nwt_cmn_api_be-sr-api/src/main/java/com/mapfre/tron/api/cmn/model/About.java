package com.mapfre.tron.api.cmn.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * About
 */
@Validated

public class About   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("dependencies")
  @Valid
  private List<String> dependencies = new ArrayList<>();

  @JsonProperty("profile")
  private String profile = null;

  public About name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Application name
   * @return name
  **/
  @ApiModelProperty(example = "nwt_lss_api_be:20.07.03-SNAPSHOT", required = true, value = "Application name")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public About dependencies(List<String> dependencies) {
    this.dependencies = dependencies;
    return this;
  }

  public About addDependenciesItem(String dependenciesItem) {
    this.dependencies.add(dependenciesItem);
    return this;
  }

  /**
   * BE and CMN dependencies version
   * @return dependencies
  **/
  @ApiModelProperty(example = "\"nwt_cmn_api_be:20.07.03-SNAPSHOT\"", required = true, value = "BE and CMN dependencies version")
  @NotNull


  public List<String> getDependencies() {
    return dependencies;
  }

  public void setDependencies(List<String> dependencies) {
    this.dependencies = dependencies;
  }

  public About profile(String profile) {
    this.profile = profile;
    return this;
  }

  /**
   * Profile
   * @return profile
  **/
  @ApiModelProperty(example = "Newtron access", required = true, value = "Profile")
  @NotNull


  public String getProfile() {
    return profile;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    About about = (About) o;
    return Objects.equals(this.name, about.name) &&
        Objects.equals(this.dependencies, about.dependencies) &&
        Objects.equals(this.profile, about.profile);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, dependencies, profile);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class About {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    dependencies: ").append(toIndentedString(dependencies)).append("\n");
    sb.append("    profile: ").append(toIndentedString(profile)).append("\n");
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

