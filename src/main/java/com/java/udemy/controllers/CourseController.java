package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.Course;
import com.java.udemy.request.CategoryRequest;
import com.java.udemy.request.CourseRequest;
import com.java.udemy.response.GetCoursesByCategoryResponse;
import com.java.udemy.response.SearchForCourseByTitleResponse;
import com.java.udemy.service.abstractions.ICourseService;
import com.java.udemy.service.abstractions.IUserService;

import jakarta.servlet.http.HttpSession;

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

    @Autowired
    private IUserService userService;

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

    @Secured({ "ROLE_TEACHER", "ROLE_ADMIN" })
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public Course createCourse(@RequestBody CourseRequest request, @NotNull HttpSession session) {
        try {
            Integer userId = userService.getSessionUserId(session);
            return courseService.createCourse(request, userId);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Secured({ "ROLE_TEACHER", "ROLE_ADMIN" })
    @PutMapping( path = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Course updateCourse(@RequestBody CourseRequest request, @PathVariable Integer id,
            @NotNull HttpSession session) {
        try {
            Integer userId = userService.getSessionUserId(session);
            return courseService.updateCourse(request, userId, id);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @Secured({ "ROLE_TEACHER", "ROLE_ADMIN" })
    @DeleteMapping( path = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCourse(@PathVariable Integer id, @NotNull HttpSession session) {
        try {
            Integer userId = userService.getSessionUserId(session);
            courseService.deleteCourse(id, userId);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }

    }
}
