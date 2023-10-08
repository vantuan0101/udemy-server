package com.java.udemy.models;

import java.time.Instant;
import java.util.Objects;

import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User {
  private static final long serialVersionUID = -1352733651057286866L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty(access = Access.READ_ONLY)
  private Integer id;

  @Column(nullable = false, length = 100)
  @Size(max = 100)
  @Pattern(regexp = "^[ a-zA-Z0-9_.'\\-]+?", message = "Invalid characters in name")
  @NotBlank
  private String fullname;

  @Column(nullable = false, unique = true)
  @Email
  @Pattern(regexp = "(^[0-9A-Za-z][\\w.\\-]+@[\\w]+\\.[\\w]\\S+\\w)$", message = "Invalid email!")
  @NotBlank
  private String email;

  @Column(length = 100)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @Transient
  @JsonProperty(access = Access.WRITE_ONLY)
  @NotBlank
  @Size(min = 8)
  private String confirmPass;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "enum('LOCAL', 'GOOGLE') DEFAULT 'LOCAL'", nullable = false)
  @JsonIgnore
  private AuthProvider authProvider = AuthProvider.LOCAL;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "enum('ROLE_STUDENT', 'ROLE_ADMIN') DEFAULT 'ROLE_STUDENT'", nullable = false)
  @JsonIgnore
  private UserRole userRole = UserRole.ROLE_STUDENT;

  @CreationTimestamp
  @Column(nullable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private Instant createdAt;

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }

  public String getUserRole() {
    return String.valueOf(userRole);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    User user = (User) o;
    return id != null && Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
