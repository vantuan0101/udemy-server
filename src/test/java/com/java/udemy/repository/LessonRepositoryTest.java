package com.java.udemy.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import com.java.udemy.models.Lesson;

@SpringBootTest
@Disabled
public class LessonRepositoryTest {
  @Autowired
  private LessonRepository lessonRepository;

  @RepeatedTest(3)
  void getFirstNotWatchedByCourseId_Test() {
    Optional<Lesson> optionalLesson = lessonRepository.getFirstNotWatchedInEnrollment(2L, 10012);
    Assertions.assertTrue(optionalLesson.isPresent());
  }

  // @Test
  // void getAllMyWatchedLessons_Test() {
  // var lessonList = lessonRepository.getWatchStatusListByEnrollment(2L, 10013);
  // Assertions.assertEquals(10, lessonList.size());
  // }
}
