package com.java.udemy.models;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sales")
@Getter
@Setter
public class Sales {
  @Id
  @Column(name = "transaction_id", nullable = false, length = 20)
  private String transactionId;

  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @JsonBackReference
  private User user;

  @Column(precision = 6, scale = 2, nullable = false)
  @NotNull
  @Min(1)
  private BigDecimal totalPaid;

  @Column(nullable = false, length = 30)
  @NotBlank
  private String paymentMethod;

  @CreationTimestamp
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Column(nullable = false)
  private Instant createdAt;
}
