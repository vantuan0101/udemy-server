package com.java.udemy.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * THIS IS JOINT TABLE BETWEEN Enrollment and Lessons
 */
@Entity
@Getter
@Setter
@Table(name = "enroll_progress", uniqueConstraints = @UniqueConstraint(columnNames = { "enrollment_id", "lesson_id" }))
public class EnrollProgress {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "enrollment_id")
  @JsonBackReference
  private Enrollment enrollment;

  @JoinColumn(name = "lesson_id")
  @JsonBackReference
  private Lesson lesson;
}
