package com.java.udemy.models;

import java.math.BigDecimal;
import java.util.Objects;
import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courses", indexes = { @Index(name = "IDX_CATEGORY", columnList = "category") })
@Getter
@Setter
@RequiredArgsConstructor
public class Course implements Serializable {
  private static final long serialVersionUID = -2540907171719494221L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  @NotBlank
  private String title;

  private String subtitle;

  // @Column(nullable = false, length = 100)
  // @NotBlank
  // @Size(max = 100)
  // private String author;

  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonBackReference
  private User user;

  @Column(length = 50, nullable = false)
  @NotBlank
  @Size(max = 50)
  private String category;

  @ColumnDefault("0.0")
  @Column(precision = 6, scale = 2, nullable = false)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private BigDecimal rating = new BigDecimal(0.0);

  @NotBlank
  @URL
  private String thumbUrl;

  @NotNull
  @Column(nullable = false, precision = 6, scale = 2)
  @Min(1)
  private BigDecimal price;

  @ColumnDefault(value = "FALSE")
  @Column(nullable = false)
  private Boolean isFeatured = false;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    Course course = (Course) o;
    return id != null && Objects.equals(id, course.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public Course(@NotBlank String title, String subtitle, User user, @NotBlank @Size(max = 50) String category,
      @NotBlank @URL String thumbUrl, @NotNull @Min(1) BigDecimal price) {
    this.title = title;
    this.subtitle = subtitle;
    this.user = user;
    this.category = category;
    this.thumbUrl = thumbUrl;
    this.price = price;
  }
  // public Course(@NotBlank String title, String subtitle, User user, @NotBlank
  // @Size(max = 50) String category,
  // BigDecimal rating, @NotBlank @URL String thumbUrl, @NotNull @Min(1)
  // BigDecimal price) {
  // this.title = title;
  // this.subtitle = subtitle;
  // this.user = user;
  // this.category = category;
  // this.rating = rating;
  // this.thumbUrl = thumbUrl;
  // this.price = price;
  // }

}
