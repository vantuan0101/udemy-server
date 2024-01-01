package com.java.udemy.controllers;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.Course;
import com.java.udemy.models.Enrollment;
import com.java.udemy.models.User;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.repository.EnrollmentRepository;
import com.java.udemy.repository.UserRepository;
import com.java.udemy.request.EnrollmentRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollUserToCourse(@RequestBody EnrollmentRequest enrollmentRequest) {
        String userEmail = enrollmentRequest.getUserEmail();
        Integer courseId = enrollmentRequest.getCourseId();

        User user = userRepository.findByEmail(userEmail).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (user != null && course != null) {
            Enrollment enrollment = new Enrollment(user, course);
            enrollmentRepository.save(enrollment);
            return ResponseEntity.ok("Enrollment saved successfully");
        } else {
            return ResponseEntity.badRequest().body("User or Course not found");
        }
    }

    // Truy vấn dữ liệu đơn giản
    @GetMapping("/all")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Integer enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElse(null);
        if (enrollment != null) {
            return ResponseEntity.ok(enrollment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

