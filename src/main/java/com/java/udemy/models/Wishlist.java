package com.java.udemy.models;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlist")
public class Wishlist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JoinColumn(name = "user_id")

  @JsonBackReference
  private User user;

  @JoinColumn(name = "course_id")
  @JsonBackReference
  private Course course;

  @CreationTimestamp
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Column(nullable = false)
  private Instant createdAt;
}
