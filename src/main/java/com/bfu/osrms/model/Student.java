package com.bfu.osrms.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * MODEL LAYER - Student Entity
 * Extends User with student-specific fields
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true, length = 191)
    private String studentId;

    @Column(nullable = false)
    private String program;

    @Column(nullable = false)
    private Integer yearOfStudy;

    public Student() {
    }

    public Student(Long id, User user, String studentId, String program, Integer yearOfStudy) {
        this.id = id;
        this.user = user;
        this.studentId = studentId;
        this.program = program;
        this.yearOfStudy = yearOfStudy;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }
    public Integer getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(Integer yearOfStudy) { this.yearOfStudy = yearOfStudy; }
}
