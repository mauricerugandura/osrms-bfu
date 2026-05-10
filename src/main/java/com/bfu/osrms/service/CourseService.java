package com.bfu.osrms.service;

import com.bfu.osrms.model.Course;
import com.bfu.osrms.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE LAYER - CourseService
 * Handles course-related business logic
 */
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Get all courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Get course by ID
     */
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    /**
     * Get course by course code
     */
    public Optional<Course> getCourseByCourseId(String courseId) {
        return courseRepository.findByCourseId(courseId);
    }

    /**
     * Get courses by semester
     */
    public List<Course> getCoursesBySemester(String semester) {
        return courseRepository.findBySemester(semester);
    }

    /**
     * Create or update course
     */
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Delete course
     */
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
