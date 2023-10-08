package com.java.udemy.models;

import java.util.UUID;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lessons")
@Getter
@Setter
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(nullable = false)
  @NotBlank
  private String lessonName;

  @NotBlank
  @Column(nullable = false, unique = true, length = 20)
  @Size(max = 20)
  private String videokey;

  @NotNull
  @ColumnDefault("0")
  private Integer lengthSeconds;

  @NotNull
  @Column(nullable = false)
  private Integer position;

  @JoinColumn(name = "course_id")
  @JsonBackReference
  private Course course;

}
