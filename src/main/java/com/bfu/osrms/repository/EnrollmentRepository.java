package com.bfu.osrms.repository;

import com.bfu.osrms.model.Enrollment;
import com.bfu.osrms.model.Student;
import com.bfu.osrms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * REPOSITORY LAYER - EnrollmentRepository
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByEnrollmentId(String enrollmentId);
    List<Enrollment> findByStudent(Student student);
    List<Enrollment> findByStudentAndSemester(Student student, String semester);
    List<Enrollment> findByCourse(Course course);
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);
}
