package com.java.udemy.models;

import java.util.Objects;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "lessons", uniqueConstraints = @UniqueConstraint(columnNames = { "course_id", "position" }))
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  @NotBlank
  private String lessonName;

  // @NotBlank
  // @Column(nullable = false, unique = true, length = 20)
  // @Size(max = 20)
  // private String videokey;

  // @NotNull
  // @ColumnDefault("0")
  // private Integer lengthSeconds;

  @NotNull
  @Column(nullable = false)
  private Integer position;

  @NotNull
  private String video_url;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonBackReference
  private Course course;

  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonBackReference
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    Lesson lesson = (Lesson) o;
    return id != null && Objects.equals(id, lesson.id);
  }

  /* convert to mm:ss */
  // public String getLengthSeconds() {
  // return String.format("%02d:%02d",
  // Duration.ofSeconds(this.lengthSeconds).toMinutesPart(),
  // Duration.ofSeconds(this.lengthSeconds).toSecondsPart());
  // }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public Lesson(String lessonName, String video_url, Integer position, Course course, User user) {
    this.lessonName = lessonName;
    this.video_url = video_url;
    this.position = position;
    this.course = course;
    this.user = user;
  }

  // public Lesson(String lessonName, String video_url, String videokey, Integer
  // lengthSeconds,
  // Integer position, Course course) {
  // this.lessonName = lessonName;
  // this.video_url = video_url;
  // this.lengthSeconds = lengthSeconds;
  // this.position = position;
  // this.videokey = videokey;
  // this.course = course;
  // }

}
