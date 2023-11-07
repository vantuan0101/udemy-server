package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.Course;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.request.CategoryRequest;
import com.java.udemy.response.GetCoursesByCategoryResponse;
import com.java.udemy.response.SearchForCourseByTitleResponse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping(path = "/id/{id}")
    public Optional<Course> getCourseById(@PathVariable @NotNull Integer id) {
        try {
            return courseRepository.findById(id);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @GetMapping(path = "/cat/{category}")
    @ResponseStatus(value = HttpStatus.OK)
    public GetCoursesByCategoryResponse getCoursesByCategory(@PathVariable @NotBlank String category) {
        try {
            List<Course> courseList = courseRepository.getCoursesByCategoryEquals(category);
            if (courseList.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results for given category");
            }
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
            List<Course> courseList = courseRepository.getTop6CoursesByIsFeatured(true);
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
            List<CategoryRequest> categories = courseRepository.getAllDistinctCategories();
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
            if (title.length() < 3) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Search query too short");
            }
            Slice<Course> searchForCourseByTitles = courseRepository.getCoursesByTitleContaining(title,
                    PageRequest.of(page, 10));
            SearchForCourseByTitleResponse response = new SearchForCourseByTitleResponse();
            response.setSearchForCourseByTitle(searchForCourseByTitles);
            return response;
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}
