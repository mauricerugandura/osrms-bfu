package com.bfu.osrms.service;

import com.bfu.osrms.model.Course;
import com.bfu.osrms.model.Enrollment;
import com.bfu.osrms.model.Student;
import com.bfu.osrms.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * SERVICE LAYER - EnrollmentService
 * Links students to the courses they are allowed to study and receive grades for.
 */
@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> getEnrollmentsByCourse(Course course) {
        return enrollmentRepository.findByCourse(course);
    }

    public Optional<Enrollment> getEnrollment(Student student, Course course) {
        return enrollmentRepository.findByStudentAndCourse(student, course);
    }

    public Enrollment enrollStudent(Student student, Course course, String semester) {
        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByStudentAndCourse(student, course);
        if (existingEnrollment.isPresent()) {
            return existingEnrollment.get();
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId("ENR-" + student.getStudentId() + "-" + course.getCourseId());
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setSemester(semester);
        enrollment.setEnrolledAt(LocalDateTime.now());
        return enrollmentRepository.save(enrollment);
    }
}
