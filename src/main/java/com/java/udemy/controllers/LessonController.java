package com.java.udemy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.java.udemy.exception.BadRequestException;
import com.java.udemy.models.Lesson;
import com.java.udemy.response.GetAllMyLessonsInEnrollmentResponse;
import com.java.udemy.response.GetLessonsByCourseIdResponse;
import com.java.udemy.service.abstractions.ILessonService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/lessons", produces = MediaType.APPLICATION_JSON_VALUE)
public class LessonController {

    @Autowired
    private ILessonService lessonService;

    @GetMapping(path = "/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetLessonsByCourseIdResponse getLessonsByCourseId(@PathVariable @NotNull Integer id,
            @RequestParam(defaultValue = "0") Integer page) {
        try {
            Slice<Lesson> lessons = lessonService.getLessonsByCourseId(id, page);
            GetLessonsByCourseIdResponse response = new GetLessonsByCourseIdResponse();
            response.setGetLessonsByCourseId(lessons);
            return response;
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }

    }

    @GetMapping(path = "/c/{courseId}/e/{enrollId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured(value = "ROLE_STUDENT")
    public GetAllMyLessonsInEnrollmentResponse getAllMyLessonsInEnrollment(@PathVariable Integer courseId,
            @PathVariable Long enrollId) {
        try {
            List<Map<String, Object>> lessons = lessonService.getWatchStatusListByEnrollment(courseId, enrollId);
            GetAllMyLessonsInEnrollmentResponse response = new GetAllMyLessonsInEnrollmentResponse();
            response.setGetAllMyLessonsInEnrollment(lessons);
            return response;
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

}
