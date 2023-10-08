package com.java.udemy.models;

import jakarta.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "course_objectives")
@Getter
@Setter
public class CourseObjective {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JoinColumn(name = "course_id")
  @JsonBackReference
  private Course course;

  @Size(max = 200)
  private String objective;

}
