package com.java.udemy.models;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "cart")
@Getter
@Setter
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JoinColumn(name = "course_id")
  @JsonBackReference
  private Course course;

  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

  @NotNull
  @Column(nullable = false, scale = 2, precision = 6)
  private BigDecimal price;

  @CreationTimestamp
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Column(nullable = false)
  private Instant createdAt;
}
