package com.bfu.osrms.repository;

import com.bfu.osrms.model.Result;
import com.bfu.osrms.model.Student;
import com.bfu.osrms.model.Course;
import com.bfu.osrms.model.Lecturer;
import com.bfu.osrms.model.ResultStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * REPOSITORY LAYER - ResultRepository
 */
@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByResultId(String resultId);
    List<Result> findByStudent(Student student);
    Optional<Result> findByStudentAndCourse(Student student, Course course);
    List<Result> findByStatus(ResultStatus status);
    List<Result> findByStudentAndStatus(Student student, ResultStatus status);
    List<Result> findByCourse(Course course);
    List<Result> findByLecturer(Lecturer lecturer);
}
