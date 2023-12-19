package com.java.udemy.service.abstractions;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Slice;

import com.java.udemy.models.Lesson;
import com.java.udemy.request.LessonRequest;

public interface ILessonService {
  Slice<Lesson> getLessonsByCourseId(Integer id, Integer page);

  // List<Map<String, Object>> getWatchStatusListByEnrollment(Integer courseId,
  // Long enrollId);

  Lesson createLesson(LessonRequest request);
}