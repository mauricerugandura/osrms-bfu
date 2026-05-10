package com.bfu.osrms.service;

import com.bfu.osrms.model.Result;
import com.bfu.osrms.model.ResultStatus;
import com.bfu.osrms.model.Student;
import com.bfu.osrms.model.Course;
import com.bfu.osrms.model.Lecturer;
import com.bfu.osrms.repository.CourseRepository;
import com.bfu.osrms.repository.EnrollmentRepository;
import com.bfu.osrms.repository.ResultRepository;
import com.bfu.osrms.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * SERVICE LAYER - ResultService
 * Handles result management and grade calculation
 */
@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TranscriptService transcriptService;

    public ResultService(ResultRepository resultRepository, StudentRepository studentRepository,
                         CourseRepository courseRepository, EnrollmentRepository enrollmentRepository,
                         TranscriptService transcriptService) {
        this.resultRepository = resultRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.transcriptService = transcriptService;
    }

    /**
     * Calculate grade based on score
     * 70-100 = A, 60-69 = B, 50-59 = C, 40-49 = D, below 40 = F
     */
    public String calculateGrade(Double score) {
        if (score >= 70) {
            return "A";
        } else if (score >= 60) {
            return "B";
        } else if (score >= 50) {
            return "C";
        } else if (score >= 40) {
            return "D";
        } else {
            return "F";
        }
    }

    /**
     * Create or update result
     */
    public Result saveResult(Result result) {
        result.calculateGrade();
        if (result.getSubmittedAt() == null) {
            result.setSubmittedAt(LocalDateTime.now());
        }
        return resultRepository.save(result);
    }

    /**
     * Submit a grade only when the student is enrolled in the selected course.
     */
    public Result submitGrade(String studentId, String courseId, Double score) {
        return submitGrade(studentId, courseId, score, null);
    }

    /**
     * Submit a grade and assign ownership to the lecturer who entered it.
     */
    public Result submitGrade(String studentId, String courseId, Double score, Lecturer lecturer) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentId));
        Course course = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));

        if (enrollmentRepository.findByStudentAndCourse(student, course).isEmpty()) {
            throw new IllegalArgumentException("This student is not enrolled in " + course.getCourseId());
        }

        Result result = resultRepository.findByStudentAndCourse(student, course).orElseGet(Result::new);
        if (result.getResultId() == null) {
            result.setResultId("RES-" + student.getStudentId() + "-" + course.getCourseId());
        }
        result.setStudent(student);
        result.setCourse(course);
        result.setLecturer(lecturer);
        result.setScore(score);
        result.setStatus(ResultStatus.PENDING);
        result.setSubmittedAt(LocalDateTime.now());
        result.setApprovedAt(null);
        result.setPublishedAt(null);
        return saveResult(result);
    }

    /**
     * Get results submitted by a lecturer.
     */
    public List<Result> getResultsByLecturer(Lecturer lecturer) {
        return resultRepository.findByLecturer(lecturer);
    }

    /**
     * Update a lecturer-owned result and send it back to registrar review.
     */
    public Result updateLecturerResult(Long resultId, Double score, Lecturer lecturer) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("Result not found"));

        if (result.getLecturer() != null && !result.getLecturer().getId().equals(lecturer.getId())) {
            throw new IllegalArgumentException("You can only update results you submitted");
        }

        Student student = result.getStudent();
        boolean wasPublished = result.getStatus() == ResultStatus.PUBLISHED;
        result.setLecturer(lecturer);
        result.setScore(score);
        result.calculateGrade();
        result.setStatus(ResultStatus.PENDING);
        result.setSubmittedAt(LocalDateTime.now());
        result.setApprovedAt(null);
        result.setPublishedAt(null);
        Result savedResult = resultRepository.save(result);

        if (wasPublished) {
            transcriptService.generateTranscript(student);
        }

        return savedResult;
    }

    /**
     * Get result by ID
     */
    public Optional<Result> getResultById(Long id) {
        return resultRepository.findById(id);
    }

    /**
     * Get results for a student
     */
    public List<Result> getResultsByStudent(Student student) {
        return resultRepository.findByStudent(student);
    }

    /**
     * Get results for a course
     */
    public List<Result> getResultsByCourse(Course course) {
        return resultRepository.findByCourse(course);
    }

    /**
     * Get pending results (awaiting approval)
     */
    public List<Result> getPendingResults() {
        return resultRepository.findByStatus(ResultStatus.PENDING);
    }

    /**
     * Get approved results awaiting publication.
     */
    public List<Result> getApprovedResults() {
        return resultRepository.findByStatus(ResultStatus.APPROVED);
    }

    /**
     * Get results already published to students.
     */
    public List<Result> getPublishedResults() {
        return resultRepository.findByStatus(ResultStatus.PUBLISHED);
    }

    /**
     * Approve result (registrar action)
     */
    public Result approveResult(Long resultId) {
        Optional<Result> result = resultRepository.findById(resultId);
        if (result.isPresent()) {
            Result r = result.get();
            r.setStatus(ResultStatus.APPROVED);
            r.setApprovedAt(LocalDateTime.now());
            return resultRepository.save(r);
        }
        return null;
    }

    /**
     * Publish result (make visible to student)
     */
    public Result publishResult(Long resultId) {
        Optional<Result> result = resultRepository.findById(resultId);
        if (result.isPresent()) {
            Result r = result.get();
            if (r.getStatus() == ResultStatus.PENDING) {
                r.setApprovedAt(LocalDateTime.now());
            }
            r.setStatus(ResultStatus.PUBLISHED);
            r.setPublishedAt(LocalDateTime.now());
            Result savedResult = resultRepository.save(r);
            transcriptService.generateTranscript(savedResult.getStudent());
            return savedResult;
        }
        return null;
    }

    /**
     * Publish all approved results and refresh affected student transcripts.
     */
    public int publishApprovedResults() {
        List<Result> approvedResults = getApprovedResults();
        for (Result result : approvedResults) {
            publishResult(result.getId());
        }
        return approvedResults.size();
    }

    /**
     * Delete result
     */
    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }

    /**
     * Delete a lecturer-owned result and refresh transcript when needed.
     */
    public void deleteLecturerResult(Long resultId, Lecturer lecturer) {
        Result result = resultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("Result not found"));

        if (result.getLecturer() != null && !result.getLecturer().getId().equals(lecturer.getId())) {
            throw new IllegalArgumentException("You can only delete results you submitted");
        }

        Student student = result.getStudent();
        boolean wasPublished = result.getStatus() == ResultStatus.PUBLISHED;
        resultRepository.delete(result);

        if (wasPublished) {
            transcriptService.generateTranscript(student);
        }
    }
}
