package com.java.udemy.service.concretions;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.java.udemy.models.Lesson;
import com.java.udemy.repository.LessonRepository;
import com.java.udemy.service.abstractions.ILessonService;

@Service
public class LessonService implements ILessonService {
  @Autowired
  private LessonRepository lessonRepository;

  @Override
  public Slice<Lesson> getLessonsByCourseId(Integer id, Integer page) {
    Slice<Lesson> lessons = lessonRepository.getLessonsByCourseId(id, PageRequest.of(page, 10));
    return lessons;
  }

  @Override
  public List<Map<String, Object>> getWatchStatusListByEnrollment(Integer courseId, Long enrollId) {
    List<Map<String, Object>> lessons = lessonRepository.getWatchStatusListByEnrollment(enrollId, courseId);
    return lessons;
  }
}
