package com.java.udemy.service.abstractions;

import org.springframework.data.domain.Slice;
import com.java.udemy.models.Lesson;
import com.java.udemy.request.LessonRequest;

public interface ILessonService {
  Slice<Lesson> getLessonsByCourseId(Integer id, Integer page);

  // List<Map<String, Object>> getWatchStatusListByEnrollment(Integer courseId,
  // Long enrollId);

  Lesson createLesson(LessonRequest request, Integer userId);

  Lesson updateLesson(LessonRequest request, Integer userId, Integer lessonId);

  void deleteLesson(Integer lessonId, Integer userId);
}