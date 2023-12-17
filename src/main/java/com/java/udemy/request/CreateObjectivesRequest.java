package com.java.udemy.request;

import java.util.ArrayList;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateObjectivesRequest extends BaseRequest {
  @NotNull
  private Integer courseId;
  @NotEmpty
  private ArrayList<String> objectives;
}
