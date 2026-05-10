package com.bfu.osrms.service;

import com.bfu.osrms.model.Student;
import com.bfu.osrms.model.Result;
import com.bfu.osrms.model.ResultStatus;
import com.bfu.osrms.model.Transcript;
import com.bfu.osrms.repository.StudentRepository;
import com.bfu.osrms.repository.ResultRepository;
import com.bfu.osrms.repository.TranscriptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE LAYER - StudentService
 * Handles student-specific business logic
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ResultRepository resultRepository;
    private final TranscriptRepository transcriptRepository;

    public StudentService(StudentRepository studentRepository, ResultRepository resultRepository,
                          TranscriptRepository transcriptRepository) {
        this.studentRepository = studentRepository;
        this.resultRepository = resultRepository;
        this.transcriptRepository = transcriptRepository;
    }

    /**
     * Get student by student ID
     */
    public Optional<Student> getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    /**
     * Get student by user ID
     */
    public Optional<Student> getStudentByUserId(Long userId) {
        return studentRepository.findByUserId(userId);
    }

    /**
     * Get all registered students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Get all results for a student
     */
    public List<Result> getStudentResults(Student student) {
        return resultRepository.findByStudentAndStatus(student, ResultStatus.PUBLISHED);
    }

    /**
     * Get transcript for a student
     */
    public Optional<Transcript> getStudentTranscript(Student student) {
        return transcriptRepository.findByStudent(student);
    }

    /**
     * Create or update student
     */
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
}
