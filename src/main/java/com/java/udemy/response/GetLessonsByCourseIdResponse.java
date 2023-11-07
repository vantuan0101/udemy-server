package com.java.udemy.response;

import org.springframework.data.domain.Slice;

import com.java.udemy.models.Lesson;

import lombok.Data;

@Data
public class GetLessonsByCourseIdResponse {
  private Slice<Lesson> getLessonsByCourseId;
}
