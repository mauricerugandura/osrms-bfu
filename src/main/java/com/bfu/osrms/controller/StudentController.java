package com.bfu.osrms.controller;

import com.bfu.osrms.model.Student;
import com.bfu.osrms.model.Result;
import com.bfu.osrms.model.Transcript;
import com.bfu.osrms.model.User;
import com.bfu.osrms.service.StudentService;
import com.bfu.osrms.service.TranscriptService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * CONTROLLER LAYER - StudentController
 * Handles student-specific endpoints: dashboard, results, and transcript
 * MVC Pattern: Receives requests, calls services, returns views
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final TranscriptService transcriptService;

    public StudentController(StudentService studentService, TranscriptService transcriptService) {
        this.studentService = studentService;
        this.transcriptService = transcriptService;
    }

    /**
     * GET /student/dashboard - Display student dashboard
     */
    @GetMapping("/dashboard")
    public String studentDashboard(Authentication authentication, Model model) {
        User currentUser = (User) authentication.getPrincipal();
        Optional<Student> student = studentService.getStudentByUserId(currentUser.getId());

        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            model.addAttribute("results", studentService.getStudentResults(student.get()));
        }

        return "student/dashboard";
    }

    /**
     * GET /student/results - Display student results
     */
    @GetMapping("/results")
    public String studentResults(Authentication authentication, Model model) {
        User currentUser = (User) authentication.getPrincipal();
        Optional<Student> student = studentService.getStudentByUserId(currentUser.getId());

        if (student.isPresent()) {
            List<Result> results = studentService.getStudentResults(student.get());
            model.addAttribute("student", student.get());
            model.addAttribute("results", results);
        }

        return "student/results";
    }

    /**
     * GET /student/transcript - Display student transcript
     */
    @GetMapping("/transcript")
    public String studentTranscript(Authentication authentication, Model model) {
        User currentUser = (User) authentication.getPrincipal();
        Optional<Student> student = studentService.getStudentByUserId(currentUser.getId());

        if (student.isPresent()) {
            Optional<Transcript> transcript = studentService.getStudentTranscript(student.get());
            model.addAttribute("student", student.get());
            model.addAttribute("transcript", transcript.orElse(null));
        }

        return "student/transcript";
    }

    /**
     * GET /student/transcript/download - Download/export student transcript
     */
    @GetMapping("/transcript/download")
    public ResponseEntity<byte[]> downloadTranscript(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Optional<Student> student = studentService.getStudentByUserId(currentUser.getId());

        if (student.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student currentStudent = student.get();
        Transcript transcript = studentService.getStudentTranscript(currentStudent)
                .orElseGet(() -> transcriptService.generateTranscript(currentStudent));
        List<Result> results = studentService.getStudentResults(currentStudent);
        String content = buildTranscriptCsv(currentStudent, transcript, results);
        byte[] fileBytes = content.getBytes(StandardCharsets.UTF_8);
        String filename = "BFU-Transcript-" + currentStudent.getStudentId() + ".csv";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(new MediaType("text", "csv"))
                .contentLength(fileBytes.length)
                .body(fileBytes);
    }

    private String buildTranscriptCsv(Student student, Transcript transcript, List<Result> results) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        StringBuilder csv = new StringBuilder();

        csv.append("Institution,Bright Future University (BFU)\n");
        csv.append("System,Online Student Result Management System\n");
        csv.append("Document,Official Academic Transcript\n");
        csv.append("Student ID,").append(csvValue(student.getStudentId())).append("\n");
        csv.append("Student Name,").append(csvValue(student.getUser().getName())).append("\n");
        csv.append("Email,").append(csvValue(student.getUser().getEmail())).append("\n");
        csv.append("Program,").append(csvValue(student.getProgram())).append("\n");
        csv.append("Year of Study,").append(student.getYearOfStudy()).append("\n");
        csv.append("Transcript ID,").append(csvValue(transcript.getTranscriptId())).append("\n");
        csv.append("GPA,").append(String.format("%.2f", transcript.getGpa())).append("\n");
        csv.append("Generated,").append(csvValue(transcript.getGeneratedAt().format(dateFormatter))).append("\n");
        csv.append("Last Updated,").append(csvValue(transcript.getUpdatedAt().format(dateFormatter))).append("\n\n");

        csv.append("Course ID,Course Name,Score,Grade,Status,Published Date\n");
        for (Result result : results) {
            csv.append(csvValue(result.getCourse().getCourseId())).append(",");
            csv.append(csvValue(result.getCourse().getCourseName())).append(",");
            csv.append(String.format("%.2f", result.getScore())).append(",");
            csv.append(csvValue(result.getGrade())).append(",");
            csv.append(csvValue(result.getStatus().name())).append(",");
            csv.append(csvValue(result.getPublishedAt() != null ? result.getPublishedAt().format(dateFormatter) : "Not recorded")).append("\n");
        }

        return csv.toString();
    }

    private String csvValue(String value) {
        if (value == null) {
            return "";
        }
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
