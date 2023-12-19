package com.java.udemy.service.concretions;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.java.udemy.models.Course;
import com.java.udemy.models.Lesson;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.repository.LessonRepository;
import com.java.udemy.request.LessonRequest;
import com.java.udemy.service.abstractions.ILessonService;

@Service
public class LessonService implements ILessonService {
  @Autowired
  private LessonRepository lessonRepository;
  @Autowired
  private CourseRepository courseRepository;

  @Override
  public Slice<Lesson> getLessonsByCourseId(Integer id, Integer page) {
    Slice<Lesson> lessons = lessonRepository.getLessonsByCourseId(id, PageRequest.of(page, 10));
    return lessons;
  }

  // @Override
  // public List<Map<String, Object>> getWatchStatusListByEnrollment(Integer
  // courseId, Long enrollId) {
  // List<Map<String, Object>> lessons =
  // lessonRepository.getWatchStatusListByEnrollment(enrollId, courseId);
  // return lessons;
  // }

  @Override
  public Lesson createLesson(LessonRequest request) {
    Course course = courseRepository.findById(request.getCourseId()).orElseThrow();
    System.out.println("Course: " + course);
    // Lesson lesson = new Lesson(
    // request.getLessonName(),
    // request.getVideo_url(),
    // request.getVideokey(),
    // request.getLengthSeconds(),
    // request.getPosition(),
    // course);
    Lesson lesson = new Lesson(
        request.getLessonName(),
        request.getVideo_url(),
        request.getPosition(),
        course);
    Lesson lessons = lessonRepository.save(lesson);
    return lessons;
  }
}
