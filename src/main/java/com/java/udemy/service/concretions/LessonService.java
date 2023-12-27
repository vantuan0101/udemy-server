package com.java.udemy.service.concretions;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.java.udemy.models.Course;
import com.java.udemy.models.Lesson;
import com.java.udemy.models.User;
import com.java.udemy.repository.CourseRepository;
import com.java.udemy.repository.LessonRepository;
import com.java.udemy.repository.UserRepository;
import com.java.udemy.request.LessonRequest;
import com.java.udemy.service.abstractions.ILessonService;

@Service
public class LessonService implements ILessonService {
  @Autowired
  private LessonRepository lessonRepository;
  @Autowired
  private CourseRepository courseRepository;
  @Autowired
  public UserRepository userRepository;

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
  public Lesson createLesson(LessonRequest request, Integer userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found"));
    if (!Set.of("ROLE_TEACHER", "ROLE_ADMIN").contains(user.getUserRole())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }
    Course course = courseRepository.findById(request.getCourseId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course Not Found"));
    if (!course.getId().equals(request.getCourseId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Course");
    }
    Lesson lesson = new Lesson(
        request.getLessonName(),
        request.getVideo_url(),
        request.getPosition(),
        course,
        user);
    Lesson lessons = lessonRepository.save(lesson);
    return lessons;
  }

  @Override
  public Lesson updateLesson(LessonRequest request, Integer userId, Integer lessonId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found"));
    if (!Set.of("ROLE_TEACHER", "ROLE_ADMIN").contains(user.getUserRole())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }
    Course course = courseRepository.findById(request.getCourseId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course Not Found"));
    if (!course.getId().equals(request.getCourseId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Course");
    }
    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lesson Not Found"));

    if (!user.getId().equals(lesson.getUser().getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }
    lesson.setLessonName(request.getLessonName());
    lesson.setVideo_url(request.getVideo_url());
    lesson.setPosition(request.getPosition());
    return lessonRepository.save(lesson);
  }

  @Override
  public void deleteLesson(Integer lessonId, Integer userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not Found"));
    if (!Set.of("ROLE_TEACHER", "ROLE_ADMIN").contains(user.getUserRole())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }
    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lesson Not Found"));
    if (!user.getId().equals(lesson.getUser().getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
    }
    lessonRepository.deleteById(lessonId);
  }
}
