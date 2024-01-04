package com.java.udemy.models;

import java.util.Objects;

import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@RequiredArgsConstructor
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "transaction_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Sales sale;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "course_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Course course;

  public OrderItem(Sales sale, Course course) {
    this.sale = sale;
    this.course = course;
  }

  // Thêm các getter cho courseId và transactionId
  public Integer getCourseId() {
    return course.getId();
  }

  public String getTransactionId() {
    return sale.getTransactionId();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    OrderItem orderItem = (OrderItem) o;
    return id != null && Objects.equals(id, orderItem.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
