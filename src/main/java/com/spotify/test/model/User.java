package com.spotify.test.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

  private int id;
  private String name;

  @JsonProperty("username")
  private String userName;

  private String email;
  private Address address;
  private String phone;
  private String website;
  private Company company;
}
