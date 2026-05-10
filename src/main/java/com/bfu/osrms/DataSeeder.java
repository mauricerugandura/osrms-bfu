package com.bfu.osrms;

import com.bfu.osrms.model.*;
import com.bfu.osrms.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * DATA SEEDER
 * Automatically seeds the database with sample data on application startup
 * For testing and demonstration purposes
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ResultRepository resultRepository;
    private final TranscriptRepository transcriptRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, StudentRepository studentRepository,
                      LecturerRepository lecturerRepository, CourseRepository courseRepository,
                      EnrollmentRepository enrollmentRepository, ResultRepository resultRepository,
                      TranscriptRepository transcriptRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.resultRepository = resultRepository;
        this.transcriptRepository = transcriptRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only seed if no users exist
        if (userRepository.count() > 0) {
            return;
        }

        System.out.println("========== SEEDING DATABASE ==========");

        // Create Admin User
        User admin = new User();
        admin.setEmail("admin@bfu.ac.rw");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setName("BFU Administrator");
        admin.setRole(UserRole.ADMIN);
        admin.setActive(true);
        admin = userRepository.save(admin);
        System.out.println("✓ Admin created: admin@bfu.ac.rw");

        // Create Lecturer Users
        User lecturer1 = new User();
        lecturer1.setEmail("lecturer1@bfu.ac.rw");
        lecturer1.setPassword(passwordEncoder.encode("pass123"));
        lecturer1.setName("Dr. Jean Mutabazi");
        lecturer1.setRole(UserRole.LECTURER);
        lecturer1.setActive(true);
        lecturer1 = userRepository.save(lecturer1);

        Lecturer lecturerEntity1 = new Lecturer();
        lecturerEntity1.setUser(lecturer1);
        lecturerEntity1.setLecturerId("LEC001");
        lecturerEntity1.setDepartment("Computer Science");
        lecturerRepository.save(lecturerEntity1);
        System.out.println("✓ Lecturer 1 created: lecturer1@bfu.ac.rw");

        User lecturer2 = new User();
        lecturer2.setEmail("lecturer2@bfu.ac.rw");
        lecturer2.setPassword(passwordEncoder.encode("pass123"));
        lecturer2.setName("Prof. Marie Uwera");
        lecturer2.setRole(UserRole.LECTURER);
        lecturer2.setActive(true);
        lecturer2 = userRepository.save(lecturer2);

        Lecturer lecturerEntity2 = new Lecturer();
        lecturerEntity2.setUser(lecturer2);
        lecturerEntity2.setLecturerId("LEC002");
        lecturerEntity2.setDepartment("Mathematics");
        lecturerRepository.save(lecturerEntity2);
        System.out.println("✓ Lecturer 2 created: lecturer2@bfu.ac.rw");

        // Create Registrar User
        User registrar = new User();
        registrar.setEmail("registrar@bfu.ac.rw");
        registrar.setPassword(passwordEncoder.encode("pass123"));
        registrar.setName("Records Officer");
        registrar.setRole(UserRole.REGISTRAR);
        registrar.setActive(true);
        registrar = userRepository.save(registrar);
        System.out.println("✓ Registrar created: registrar@bfu.ac.rw");

        // Create Student Users
        User[] studentUsers = new User[5];
        Student[] students = new Student[5];
        String[] studentEmails = {
                "student1@bfu.ac.rw",
                "student2@bfu.ac.rw",
                "student3@bfu.ac.rw",
                "student4@bfu.ac.rw",
                "student5@bfu.ac.rw"
        };
        String[] studentNames = {
                "Jean Pierre Nkurunziza",
                "Claudette Umutesi",
                "Innocent Ndayisaba",
                "Ange Mirenge",
                "Franck Nzigamasabo"
        };
        String[] studentIds = {
                "STU001",
                "STU002",
                "STU003",
                "STU004",
                "STU005"
        };

        for (int i = 0; i < 5; i++) {
            User student = new User();
            student.setEmail(studentEmails[i]);
            student.setPassword(passwordEncoder.encode("pass123"));
            student.setName(studentNames[i]);
            student.setRole(UserRole.STUDENT);
            student.setActive(true);
            studentUsers[i] = userRepository.save(student);

            Student studentEntity = new Student();
            studentEntity.setUser(studentUsers[i]);
            studentEntity.setStudentId(studentIds[i]);
            studentEntity.setProgram("Bachelor of Science in Computer Science");
            studentEntity.setYearOfStudy(2);
            students[i] = studentRepository.save(studentEntity);
            System.out.println("✓ Student " + (i + 1) + " created: " + studentEmails[i]);
        }

        // Create Courses
        Course[] courses = new Course[3];
        String[] courseIds = {"CS101", "CS102", "MATH201"};
        String[] courseNames = {
                "Introduction to Programming",
                "Database Management Systems",
                "Advanced Mathematics"
        };

        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseId(courseIds[i]);
            course.setCourseName(courseNames[i]);
            course.setCredits(3);
            course.setSemester("Semester 2");
            course.setDescription("Course in " + courseNames[i]);
            courses[i] = courseRepository.save(course);
            System.out.println("✓ Course created: " + courseIds[i]);
        }

        // Create Enrollments
        for (Student student : students) {
            for (Course course : courses) {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId("ENR-" + student.getStudentId() + "-" + course.getCourseId());
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                enrollment.setSemester("Semester 2");
                enrollment.setEnrolledAt(LocalDateTime.now());
                enrollmentRepository.save(enrollment);
            }
        }
        System.out.println("✓ Enrollments created (5 students x 3 courses)");

        // Create Sample Results
        double[] scores = {85.5, 72.0, 91.0, 65.0, 88.0};
        for (int i = 0; i < students.length; i++) {
            for (int j = 0; j < courses.length; j++) {
                Result result = new Result();
                result.setResultId("RES-" + students[i].getStudentId() + "-" + courses[j].getCourseId());
                result.setStudent(students[i]);
                result.setCourse(courses[j]);
                result.setLecturer(j == 2 ? lecturerEntity2 : lecturerEntity1);
                result.setScore(scores[(i + j) % scores.length]);
                result.calculateGrade();
                result.setStatus(ResultStatus.PUBLISHED);
                result.setSubmittedAt(LocalDateTime.now().minusDays(10));
                result.setApprovedAt(LocalDateTime.now().minusDays(5));
                result.setPublishedAt(LocalDateTime.now().minusDays(1));
                resultRepository.save(result);
            }
        }
        System.out.println("✓ Sample results created");

        // Create Transcripts
        for (Student student : students) {
            Transcript transcript = new Transcript();
            transcript.setTranscriptId("TR-" + student.getStudentId() + "-" + System.currentTimeMillis());
            transcript.setStudent(student);
            transcript.setGpa(3.5);
            transcript.setGeneratedAt(LocalDateTime.now());
            transcript.setUpdatedAt(LocalDateTime.now());
            transcriptRepository.save(transcript);
        }
        System.out.println("✓ Transcripts created");

        System.out.println("========== DATABASE SEEDED SUCCESSFULLY ==========");
        System.out.println("\n📝 Test Credentials:");
        System.out.println("  Admin: admin@bfu.ac.rw / admin123");
        System.out.println("  Lecturer: lecturer1@bfu.ac.rw / pass123");
        System.out.println("  Student: student1@bfu.ac.rw / pass123");
        System.out.println("  Registrar: registrar@bfu.ac.rw / pass123");
        System.out.println();
    }
}
