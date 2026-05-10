-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 09, 2026 at 11:12 AM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `osrms_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `id` bigint(20) NOT NULL,
  `course_id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `course_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `credits` int(11) NOT NULL,
  `semester` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`id`, `course_id`, `course_name`, `credits`, `semester`, `description`, `created_at`, `updated_at`) VALUES
(1, 'CS101', 'Introduction to Programming', 3, 'Semester 1', 'Learn the basics of programming', '2026-05-01 12:11:58', '2026-05-01 12:11:58'),
(2, 'CS102', 'Data Structures', 3, 'Semester 2', 'Study fundamental data structures', '2026-05-01 12:11:58', '2026-05-01 12:11:58'),
(3, 'CS201', 'Web Development', 4, 'Semester 3', 'Build responsive web applications', '2026-05-01 12:11:58', '2026-05-01 12:11:58'),
(4, 'MATH8021', 'Applied Mathematics', 3, 'Semester 2', 'starting math from the scratch and help you increase your skill', '2026-05-02 14:05:05', '2026-05-02 14:05:05');

-- --------------------------------------------------------

--
-- Table structure for table `enrollments`
--

CREATE TABLE `enrollments` (
  `id` bigint(20) NOT NULL,
  `enrollment_id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  `semester` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `enrolled_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `enrollments`
--

INSERT INTO `enrollments` (`id`, `enrollment_id`, `student_id`, `course_id`, `semester`, `enrolled_at`, `created_at`, `updated_at`) VALUES
(2, 'ENR-STU001-CS101', 1, 1, 'Semester 1', '2026-05-01 13:33:29', '2026-05-01 13:33:29', '2026-05-01 13:33:29'),
(3, 'ENR-STU001-CS102', 1, 2, 'Semester 2', '2026-05-01 13:33:29', '2026-05-01 13:33:29', '2026-05-01 13:33:29'),
(4, 'ENR-STU001-CS201', 1, 3, 'Semester 3', '2026-05-01 13:33:29', '2026-05-01 13:33:29', '2026-05-01 13:33:29'),
(5, 'ENR-STU002-MATH8021', 3, 4, 'Semester 2', '2026-05-02 14:07:11', '2026-05-02 14:07:11', '2026-05-02 14:07:11');

-- --------------------------------------------------------

--
-- Table structure for table `lecturers`
--

CREATE TABLE `lecturers` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `lecturer_id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `department` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lecturers`
--

INSERT INTO `lecturers` (`id`, `user_id`, `lecturer_id`, `department`, `created_at`, `updated_at`) VALUES
(1, 4, 'LEC001', 'Computer Science', '2026-05-01 12:11:58', '2026-05-01 12:11:58');

-- --------------------------------------------------------

--
-- Table structure for table `results`
--

CREATE TABLE `results` (
  `id` bigint(20) NOT NULL,
  `result_id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  `score` double NOT NULL,
  `grade` varchar(2) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('PENDING','APPROVED','PUBLISHED') COLLATE utf8mb4_unicode_ci NOT NULL,
  `submitted_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `approved_at` timestamp NULL DEFAULT NULL,
  `published_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `results`
--

INSERT INTO `results` (`id`, `result_id`, `student_id`, `course_id`, `score`, `grade`, `status`, `submitted_at`, `approved_at`, `published_at`, `created_at`, `updated_at`) VALUES
(2, 'RES-STU002-MATH8021', 3, 4, 70, 'A', 'PENDING', '2026-05-02 14:37:40', NULL, NULL, '2026-05-02 14:37:40', '2026-05-02 14:37:40');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `student_id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `program` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `year_of_study` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `user_id`, `student_id`, `program`, `year_of_study`, `created_at`, `updated_at`) VALUES
(1, 3, 'STU001', 'Computer Science', 2, '2026-05-01 12:11:58', '2026-05-01 12:11:58'),
(3, 6, 'STU002', 'Bachelor of Software Engineering', 2, '2026-05-02 14:07:11', '2026-05-02 14:07:11');

-- --------------------------------------------------------

--
-- Table structure for table `transcripts`
--

CREATE TABLE `transcripts` (
  `id` bigint(20) NOT NULL,
  `transcript_id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `gpa` double NOT NULL,
  `generated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('ADMIN','LECTURER','STUDENT','REGISTRAR') COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `name`, `role`, `is_active`, `created_at`, `updated_at`) VALUES
(1, 'admin@bfu.ac.rw', '$2a$10$TZuthHRy6q3u6N8HaPAmueRPhrUcAnTzvtBba/D7El37vvfiEb.qC', 'BFU Administrator', 'ADMIN', 1, '2026-05-01 12:11:58', '2026-05-01 13:06:40'),
(2, 'registrar@bfu.ac.rw', '$2a$10$zNT4tF9tvmNH5ilUSrfY9OTDiaMxmxyuG2IeNvAXE9QbsNhJf9A.C', 'Records Officer', 'REGISTRAR', 1, '2026-05-01 12:11:58', '2026-05-01 13:05:54'),
(3, 'student1@bfu.ac.rw', '$2a$10$zNT4tF9tvmNH5ilUSrfY9OTDiaMxmxyuG2IeNvAXE9QbsNhJf9A.C', 'Jean Pierre Nkurunziza', 'STUDENT', 1, '2026-05-01 12:11:58', '2026-05-01 13:05:54'),
(4, 'lecturer1@bfu.ac.rw', '$2a$10$zNT4tF9tvmNH5ilUSrfY9OTDiaMxmxyuG2IeNvAXE9QbsNhJf9A.C', 'Dr. Jean Mutabazi', 'LECTURER', 1, '2026-05-01 12:11:58', '2026-05-01 13:05:54'),
(6, 'maurice@bfu.ac.rw', '$2a$10$4L6TFsbV6Md6JoWtLLix0.lGLQlR3jKLfaNj.iUMUdEHqekJYVnt2', 'Rugandura Maurice', 'STUDENT', 1, '2026-05-02 14:07:11', '2026-05-02 14:07:11');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `course_id` (`course_id`),
  ADD KEY `idx_course_id` (`course_id`),
  ADD KEY `idx_semester` (`semester`);

--
-- Indexes for table `enrollments`
--
ALTER TABLE `enrollments`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `enrollment_id` (`enrollment_id`),
  ADD UNIQUE KEY `unique_enrollment` (`student_id`,`course_id`,`semester`),
  ADD KEY `idx_enrollment_id` (`enrollment_id`),
  ADD KEY `idx_student_id` (`student_id`),
  ADD KEY `idx_course_id` (`course_id`),
  ADD KEY `idx_semester` (`semester`);

--
-- Indexes for table `lecturers`
--
ALTER TABLE `lecturers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD UNIQUE KEY `lecturer_id` (`lecturer_id`),
  ADD KEY `idx_lecturer_id` (`lecturer_id`),
  ADD KEY `idx_department` (`department`(191));

--
-- Indexes for table `results`
--
ALTER TABLE `results`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `result_id` (`result_id`),
  ADD UNIQUE KEY `unique_result` (`student_id`,`course_id`),
  ADD KEY `idx_result_id` (`result_id`),
  ADD KEY `idx_student_id` (`student_id`),
  ADD KEY `idx_course_id` (`course_id`),
  ADD KEY `idx_status` (`status`),
  ADD KEY `idx_grade` (`grade`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD UNIQUE KEY `student_id` (`student_id`),
  ADD KEY `idx_student_id` (`student_id`),
  ADD KEY `idx_program` (`program`(191));

--
-- Indexes for table `transcripts`
--
ALTER TABLE `transcripts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `transcript_id` (`transcript_id`),
  ADD UNIQUE KEY `student_id` (`student_id`),
  ADD KEY `idx_transcript_id` (`transcript_id`),
  ADD KEY `idx_student_id` (`student_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `idx_email` (`email`),
  ADD KEY `idx_role` (`role`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `enrollments`
--
ALTER TABLE `enrollments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `lecturers`
--
ALTER TABLE `lecturers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `results`
--
ALTER TABLE `results`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `transcripts`
--
ALTER TABLE `transcripts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `enrollments`
--
ALTER TABLE `enrollments`
  ADD CONSTRAINT `enrollments_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `enrollments_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `lecturers`
--
ALTER TABLE `lecturers`
  ADD CONSTRAINT `lecturers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `results`
--
ALTER TABLE `results`
  ADD CONSTRAINT `results_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `results_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `transcripts`
--
ALTER TABLE `transcripts`
  ADD CONSTRAINT `transcripts_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
