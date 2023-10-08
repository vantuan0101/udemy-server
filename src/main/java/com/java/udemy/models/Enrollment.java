package com.java.udemy.models;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import javax.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
public class Enrollment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonBackReference
  @JoinColumn(name = "user_id")
  private User user;

  @JoinColumn(name = "course_id")
  @JsonBackReference
  private Course course;

  @Column(nullable = false)
  @ColumnDefault(value = "false")
  private Boolean isCompleted = false;

  @ColumnDefault(value = "1")
  private Integer nextPosition = 1;

  @ColumnDefault("0")
  @Max(100)
  @Column(nullable = false, precision = 6, scale = 2)
  private BigDecimal progress = BigDecimal.ZERO;

  @CreationTimestamp
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Column(nullable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Instant updatedAt = null;
}
