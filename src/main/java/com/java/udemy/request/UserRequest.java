package com.java.udemy.request;

import java.time.Instant;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest extends BaseRequest {
  private Integer id;
  private String fullname;
  private String email;
  private Instant createdAt;

  public UserRequest(Integer id, String fullname, String email, Instant createdAt) {
    this.id = id;
    this.fullname = fullname;
    this.email = email;
    this.createdAt = createdAt;
  }
}
