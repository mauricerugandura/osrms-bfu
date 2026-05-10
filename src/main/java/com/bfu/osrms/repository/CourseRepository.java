package com.bfu.osrms.repository;

import com.bfu.osrms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * REPOSITORY LAYER - CourseRepository
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseId(String courseId);
    List<Course> findBySemester(String semester);
}
