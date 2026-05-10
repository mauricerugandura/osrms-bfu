package com.bfu.osrms.controller;

import com.bfu.osrms.model.Course;
import com.bfu.osrms.model.Lecturer;
import com.bfu.osrms.model.Student;
import com.bfu.osrms.model.User;
import com.bfu.osrms.model.UserRole;
import com.bfu.osrms.service.EnrollmentService;
import com.bfu.osrms.service.LecturerService;
import com.bfu.osrms.service.ResultService;
import com.bfu.osrms.service.StudentService;
import com.bfu.osrms.service.UserService;
import com.bfu.osrms.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * CONTROLLER LAYER - AdminController
 * Handles admin-specific endpoints: dashboard, user management, and course management
 * MVC Pattern: Manages system-wide administration operations
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;
    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final EnrollmentService enrollmentService;
    private final ResultService resultService;

    public AdminController(UserService userService, CourseService courseService, StudentService studentService,
                           LecturerService lecturerService, EnrollmentService enrollmentService,
                           ResultService resultService) {
        this.userService = userService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
        this.enrollmentService = enrollmentService;
        this.resultService = resultService;
    }

    /**
     * GET /admin/dashboard - Display admin dashboard
     */
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("totalCourses", courseService.getAllCourses().size());
        model.addAttribute("totalStudents", studentService.getAllStudents().size());
        model.addAttribute("totalLecturers", lecturerService.getAllLecturers().size());
        model.addAttribute("totalEnrollments", enrollmentService.getAllEnrollments().size());
        return "admin/dashboard";
    }

    /**
     * GET /admin/users - List all users
     */
    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("lecturers", lecturerService.getAllLecturers());
        model.addAttribute("courses", courseService.getAllCourses());
        return "admin/users";
    }

    /**
     * POST /admin/students - Register a student and enroll them into selected courses.
     */
    @PostMapping("/students")
    public String registerStudent(@RequestParam String name,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String studentId,
                                  @RequestParam String program,
                                  @RequestParam Integer yearOfStudy,
                                  @RequestParam(value = "courseIds", required = false) List<Long> courseIds) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(UserRole.STUDENT);
        user.setActive(true);
        User savedUser = userService.register(user);

        Student student = new Student();
        student.setUser(savedUser);
        student.setStudentId(studentId);
        student.setProgram(program);
        student.setYearOfStudy(yearOfStudy);
        Student savedStudent = studentService.saveStudent(student);

        if (courseIds != null) {
            for (Long courseId : courseIds) {
                courseService.getCourseById(courseId)
                        .ifPresent(course -> enrollmentService.enrollStudent(savedStudent, course, course.getSemester()));
            }
        }

        return "redirect:/admin/users?studentCreated=true";
    }

    /**
     * POST /admin/lecturers - Register a lecturer account.
     */
    @PostMapping("/lecturers")
    public String registerLecturer(@RequestParam String name,
                                   @RequestParam String email,
                                   @RequestParam String password,
                                   @RequestParam String lecturerId,
                                   @RequestParam String department) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(UserRole.LECTURER);
        user.setActive(true);
        User savedUser = userService.register(user);

        Lecturer lecturer = new Lecturer();
        lecturer.setUser(savedUser);
        lecturer.setLecturerId(lecturerId);
        lecturer.setDepartment(department);
        lecturerService.saveLecturer(lecturer);

        return "redirect:/admin/users?lecturerCreated=true";
    }

    /**
     * GET /admin/courses - List all courses
     */
    @GetMapping("/courses")
    public String manageCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("newCourse", new Course());
        return "admin/courses";
    }

    /**
     * POST /admin/courses - Add new course
     */
    @PostMapping("/courses")
    public String addCourse(@ModelAttribute Course course) {
        courseService.saveCourse(course);
        return "redirect:/admin/courses?created=true";
    }

    /**
     * GET /admin/courses/{id}/delete - Delete course
     */
    @GetMapping("/courses/{id}/delete")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/admin/courses?deleted=true";
    }

    /**
     * GET /admin/publish - Publish results
     */
    @GetMapping("/publish")
    public String publishResults(Model model) {
        model.addAttribute("publishedResults", resultService.getPublishedResults());
        return "admin/publish";
    }
}
