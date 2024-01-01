package com.java.udemy.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentRequest {
    private String userEmail;
    private Integer courseId;
}
