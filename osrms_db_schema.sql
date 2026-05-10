-- =========================================
-- OSRMS Database Schema Creation Script
-- Database: osrms_db
-- =========================================

-- Create USERS table (base user table)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(191) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create STUDENTS table
CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    student_id VARCHAR(191) NOT NULL UNIQUE,
    program VARCHAR(255) NOT NULL,
    year_of_study INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_student_id (student_id),
    INDEX idx_program (program)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create LECTURERS table
CREATE TABLE IF NOT EXISTS lecturers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    lecturer_id VARCHAR(191) NOT NULL UNIQUE,
    department VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_lecturer_id (lecturer_id),
    INDEX idx_department (department)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create COURSES table
CREATE TABLE IF NOT EXISTS courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id VARCHAR(191) NOT NULL UNIQUE,
    course_name VARCHAR(255) NOT NULL,
    credits INT NOT NULL,
    semester VARCHAR(50) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_course_id (course_id),
    INDEX idx_semester (semester)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create ENROLLMENTS table
CREATE TABLE IF NOT EXISTS enrollments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id VARCHAR(191) NOT NULL UNIQUE,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    semester VARCHAR(50) NOT NULL,
    enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    UNIQUE KEY unique_enrollment (student_id, course_id, semester),
    INDEX idx_enrollment_id (enrollment_id),
    INDEX idx_student_id (student_id),
    INDEX idx_course_id (course_id),
    INDEX idx_semester (semester)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create RESULTS table
CREATE TABLE IF NOT EXISTS results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    result_id VARCHAR(191) NOT NULL UNIQUE,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    score DOUBLE NOT NULL,
    grade VARCHAR(2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved_at TIMESTAMP NULL,
    published_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    UNIQUE KEY unique_result (student_id, course_id),
    INDEX idx_result_id (result_id),
    INDEX idx_student_id (student_id),
    INDEX idx_course_id (course_id),
    INDEX idx_status (status),
    INDEX idx_grade (grade)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create TRANSCRIPTS table
CREATE TABLE IF NOT EXISTS transcripts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transcript_id VARCHAR(191) NOT NULL UNIQUE,
    student_id BIGINT NOT NULL UNIQUE,
    gpa DOUBLE NOT NULL,
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    INDEX idx_transcript_id (transcript_id),
    INDEX idx_student_id (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================
-- Optional: Insert Sample Data
-- =========================================

-- Insert sample users
-- Admin password: admin123
-- Registrar/Lecturer/Student password: pass123
INSERT IGNORE INTO users (email, password, name, role, is_active) VALUES
('admin@bfu.ac.rw', '$2a$10$TZuthHRy6q3u6N8HaPAmueRPhrUcAnTzvtBba/D7El37vvfiEb.qC', 'BFU Administrator', 'ADMIN', true),
('registrar@bfu.ac.rw', '$2a$10$zNT4tF9tvmNH5ilUSrfY9OTDiaMxmxyuG2IeNvAXE9QbsNhJf9A.C', 'Records Officer', 'REGISTRAR', true),
('student1@bfu.ac.rw', '$2a$10$zNT4tF9tvmNH5ilUSrfY9OTDiaMxmxyuG2IeNvAXE9QbsNhJf9A.C', 'Jean Pierre Nkurunziza', 'STUDENT', true),
('lecturer1@bfu.ac.rw', '$2a$10$zNT4tF9tvmNH5ilUSrfY9OTDiaMxmxyuG2IeNvAXE9QbsNhJf9A.C', 'Dr. Jean Mutabazi', 'LECTURER', true);

-- Insert sample student
INSERT IGNORE INTO students (user_id, student_id, program, year_of_study) VALUES
((SELECT id FROM users WHERE email = 'student1@bfu.ac.rw'), 'STU001', 'Computer Science', 2);

-- Insert sample lecturer
INSERT IGNORE INTO lecturers (user_id, lecturer_id, department) VALUES
((SELECT id FROM users WHERE email = 'lecturer1@bfu.ac.rw'), 'LEC001', 'Computer Science');

-- Insert sample courses
INSERT IGNORE INTO courses (course_id, course_name, credits, semester, description) VALUES
('CS101', 'Introduction to Programming', 3, 'Semester 1', 'Learn the basics of programming'),
('CS102', 'Data Structures', 3, 'Semester 2', 'Study fundamental data structures'),
('CS201', 'Web Development', 4, 'Semester 3', 'Build responsive web applications');

-- Enroll the sample student into the sample courses
INSERT IGNORE INTO enrollments (enrollment_id, student_id, course_id, semester)
SELECT CONCAT('ENR-', s.student_id, '-', c.course_id), s.id, c.id, c.semester
FROM students s
CROSS JOIN courses c
WHERE s.student_id = 'STU001';

-- =========================================
-- Show Table Status
-- =========================================
SHOW TABLES;
