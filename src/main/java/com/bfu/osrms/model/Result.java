package com.bfu.osrms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * MODEL LAYER - Result Entity
 * Represents a student's result in a course
 */
@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 191)
    private String resultId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @Column(nullable = false)
    private Double score;

    @Column(nullable = false)
    private String grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultStatus status = ResultStatus.PENDING;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime submittedAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime approvedAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime publishedAt;

    public Result() {
    }

    public Result(Long id, String resultId, Student student, Course course, Lecturer lecturer, Double score, String grade,
                  ResultStatus status, LocalDateTime submittedAt, LocalDateTime approvedAt, LocalDateTime publishedAt) {
        this.id = id;
        this.resultId = resultId;
        this.student = student;
        this.course = course;
        this.lecturer = lecturer;
        this.score = score;
        this.grade = grade;
        this.status = status;
        this.submittedAt = submittedAt;
        this.approvedAt = approvedAt;
        this.publishedAt = publishedAt;
    }

    public void calculateGrade() {
        if (score >= 70) {
            this.grade = "A";
        } else if (score >= 60) {
            this.grade = "B";
        } else if (score >= 50) {
            this.grade = "C";
        } else if (score >= 40) {
            this.grade = "D";
        } else {
            this.grade = "F";
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getResultId() { return resultId; }
    public void setResultId(String resultId) { this.resultId = resultId; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public Lecturer getLecturer() { return lecturer; }
    public void setLecturer(Lecturer lecturer) { this.lecturer = lecturer; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public ResultStatus getStatus() { return status; }
    public void setStatus(ResultStatus status) { this.status = status; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
}
