package com.java.udemy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.udemy.models.CourseObjective;

import java.util.List;

@Repository
public interface ObjectiveRepository extends CrudRepository<CourseObjective, Integer> {

  List<CourseObjective> getCourseObjectivesByCourseId(Integer course_id);

}
