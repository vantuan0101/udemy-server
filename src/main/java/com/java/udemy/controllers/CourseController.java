package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.Course;
import com.java.udemy.request.CategoryRequest;
import com.java.udemy.response.GetCoursesByCategoryResponse;
import com.java.udemy.response.SearchForCourseByTitleResponse;
import com.java.udemy.service.abstractions.ICourseService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @GetMapping(path = "/id/{id}")
    public Optional<Course> getCourseById(@PathVariable @NotNull Integer id) {
        try {
            return courseService.findCourseById(id);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "/cat/{category}")
    @ResponseStatus(value = HttpStatus.OK)
    public GetCoursesByCategoryResponse getCoursesByCategory(@PathVariable @NotBlank String category) {
        try {
            List<Course> courseList = courseService.getCoursesByCategoryEquals(category);
            GetCoursesByCategoryResponse response = new GetCoursesByCategoryResponse();
            response.setGetCoursesByCategory(courseList);
            return response;
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "/top")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<Course>> getAllTopCourses() {
        try {
            List<Course> courseList = courseService.getTop6CoursesByIsFeatured(true);
            CacheControl cc = CacheControl.maxAge(60, TimeUnit.MINUTES).cachePublic();
            return ResponseEntity.ok().cacheControl(cc).body(courseList);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "/categories")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<CategoryRequest>> getCategoryListDistinct() {
        try {
            List<CategoryRequest> categories = courseService.getAllDistinctCategories();
            CacheControl cc = CacheControl.maxAge(60, TimeUnit.MINUTES).cachePublic();
            return ResponseEntity.ok().cacheControl(cc).body(categories);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "/search")
    @ResponseStatus(value = HttpStatus.OK)
    public SearchForCourseByTitleResponse searchForCourseByTitle(
            @RequestParam(defaultValue = "") @NotBlank String title,
            @RequestParam(defaultValue = "0") Integer page) {
        try {
            Slice<Course> searchForCourseByTitles = courseService.getCoursesByTitleContaining(title, page);
            SearchForCourseByTitleResponse response = new SearchForCourseByTitleResponse();
            response.setSearchForCourseByTitle(searchForCourseByTitles);
            return response;
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Course> insertCourse(@RequestBody Course course) {
        try {
            Course insertedCourse = courseService.insertCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedCourse);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Course> updateCourse(
            @PathVariable @NotNull Integer id,
            @RequestBody Course updatedCourse) {
        try {
            Course updated = courseService.updateCourse(id, updatedCourse);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCourse(@PathVariable @NotNull Integer id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}


