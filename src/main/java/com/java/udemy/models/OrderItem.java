package com.java.udemy.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonBackReference
  @JoinColumn(name = "transaction_id")
  private Sales sale;

  @JsonBackReference
  @JoinColumn(name = "course_id")
  private Course course;
}
