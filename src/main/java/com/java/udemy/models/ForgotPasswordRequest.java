package com.java.udemy.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ForgotPasswordRequest {
    @Id
    private String email;
}
