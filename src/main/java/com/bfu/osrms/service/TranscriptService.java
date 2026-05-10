package com.bfu.osrms.service;

import com.bfu.osrms.model.Transcript;
import com.bfu.osrms.model.Student;
import com.bfu.osrms.model.Result;
import com.bfu.osrms.model.ResultStatus;
import com.bfu.osrms.repository.TranscriptRepository;
import com.bfu.osrms.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * SERVICE LAYER - TranscriptService
 * Handles transcript generation and GPA calculation
 */
@Service
public class TranscriptService {

    private final TranscriptRepository transcriptRepository;
    private final ResultRepository resultRepository;

    public TranscriptService(TranscriptRepository transcriptRepository, ResultRepository resultRepository) {
        this.transcriptRepository = transcriptRepository;
        this.resultRepository = resultRepository;
    }

    /**
     * Generate or update transcript for a student
     * Calculates GPA from all published results
     */
    public Transcript generateTranscript(Student student) {
        List<Result> publishedResults = resultRepository.findByStudentAndStatus(student, ResultStatus.PUBLISHED);

        // Calculate GPA (simple average of numeric grades)
        Double totalGpa = 0.0;
        int count = 0;

        for (Result result : publishedResults) {
            Double gradePoint = getGradePoint(result.getGrade());
            totalGpa += gradePoint;
            count++;
        }

        Double gpa = count > 0 ? totalGpa / count : 0.0;

        // Find existing transcript or create new one
        Optional<Transcript> existingTranscript = transcriptRepository.findByStudent(student);
        Transcript transcript;

        if (existingTranscript.isPresent()) {
            transcript = existingTranscript.get();
            transcript.setGpa(gpa);
            transcript.setUpdatedAt(LocalDateTime.now());
        } else {
            transcript = new Transcript();
            transcript.setStudent(student);
            transcript.setGpa(gpa);
            transcript.setGeneratedAt(LocalDateTime.now());
            transcript.setUpdatedAt(LocalDateTime.now());
            transcript.setTranscriptId("TR-" + student.getStudentId() + "-" + System.currentTimeMillis());
        }

        return transcriptRepository.save(transcript);
    }

    /**
     * Get transcript for a student
     */
    public Optional<Transcript> getTranscriptByStudent(Student student) {
        return transcriptRepository.findByStudent(student);
    }

    /**
     * Convert letter grade to numeric grade point (4.0 scale)
     */
    private Double getGradePoint(String grade) {
        return switch (grade) {
            case "A" -> 4.0;
            case "B" -> 3.0;
            case "C" -> 2.0;
            case "D" -> 1.0;
            case "F" -> 0.0;
            default -> 0.0;
        };
    }
}
