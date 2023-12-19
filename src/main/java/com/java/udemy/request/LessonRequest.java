package com.java.udemy.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LessonRequest extends BaseRequest {
  @NotNull
  private String lessonName;
  @NotNull
  private String video_url;
  @NotNull
  private Integer courseId;
  @NotNull
  private Integer position;
}
