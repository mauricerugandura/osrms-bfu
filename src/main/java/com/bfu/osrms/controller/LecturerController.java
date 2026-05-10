package com.bfu.osrms.controller;

import com.bfu.osrms.model.Lecturer;
import com.bfu.osrms.model.User;
import com.bfu.osrms.service.CourseService;
import com.bfu.osrms.service.EnrollmentService;
import com.bfu.osrms.service.LecturerService;
import com.bfu.osrms.service.ResultService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

/**
 * CONTROLLER LAYER - LecturerController
 * Handles lecturer-specific endpoints: dashboard, grades, and result submission
 * MVC Pattern: Processes lecturer requests for entering and managing grades
 */
@Controller
@RequestMapping("/lecturer")
public class LecturerController {

    private final LecturerService lecturerService;
    private final ResultService resultService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public LecturerController(LecturerService lecturerService, ResultService resultService,
                              CourseService courseService, EnrollmentService enrollmentService) {
        this.lecturerService = lecturerService;
        this.resultService = resultService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    /**
     * GET /lecturer/dashboard - Display lecturer dashboard
     */
    @GetMapping("/dashboard")
    public String lecturerDashboard(Authentication authentication, Model model) {
        User currentUser = (User) authentication.getPrincipal();
        Optional<Lecturer> lecturer = lecturerService.getLecturerByUserId(currentUser.getId());

        if (lecturer.isPresent()) {
            model.addAttribute("lecturer", lecturer.get());
            model.addAttribute("myResults", resultService.getResultsByLecturer(lecturer.get()).size());
        }
        model.addAttribute("totalCourses", courseService.getAllCourses().size());
        model.addAttribute("totalEnrollments", enrollmentService.getAllEnrollments().size());
        model.addAttribute("pendingResults", resultService.getPendingResults().size());

        return "lecturer/dashboard";
    }

    /**
     * GET /lecturer/grades - View grades form
     */
    @GetMapping("/grades")
    public String gradesForm(Authentication authentication, Model model) {
        Optional<Lecturer> lecturer = getCurrentLecturer(authentication);
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("enrollments", enrollmentService.getAllEnrollments());
        model.addAttribute("results", lecturer.map(resultService::getResultsByLecturer).orElse(List.of()));
        return "lecturer/grades";
    }

    /**
     * POST /lecturer/grades/submit - Submit/update grades
     */
    @PostMapping("/grades/submit")
    public String submitGrades(Authentication authentication,
                               @RequestParam String studentId,
                               @RequestParam String courseId,
                               @RequestParam Double score) {
        try {
            Lecturer lecturer = getCurrentLecturer(authentication)
                    .orElseThrow(() -> new IllegalArgumentException("Lecturer profile not found"));
            resultService.submitGrade(studentId, courseId, score, lecturer);
            return "redirect:/lecturer/grades?success=true";
        } catch (IllegalArgumentException ex) {
            return "redirect:/lecturer/grades?error=" + ex.getMessage().replace(" ", "+");
        }
    }

    /**
     * POST /lecturer/results/{id}/update - Update a submitted result after a claim.
     */
    @PostMapping("/results/{id}/update")
    public String updateResult(Authentication authentication,
                               @PathVariable Long id,
                               @RequestParam Double score) {
        try {
            Lecturer lecturer = getCurrentLecturer(authentication)
                    .orElseThrow(() -> new IllegalArgumentException("Lecturer profile not found"));
            resultService.updateLecturerResult(id, score, lecturer);
            return "redirect:/lecturer/grades?updated=true";
        } catch (IllegalArgumentException ex) {
            return "redirect:/lecturer/grades?error=" + ex.getMessage().replace(" ", "+");
        }
    }

    /**
     * POST /lecturer/results/{id}/delete - Delete a submitted result when necessary.
     */
    @PostMapping("/results/{id}/delete")
    public String deleteResult(Authentication authentication,
                               @PathVariable Long id) {
        try {
            Lecturer lecturer = getCurrentLecturer(authentication)
                    .orElseThrow(() -> new IllegalArgumentException("Lecturer profile not found"));
            resultService.deleteLecturerResult(id, lecturer);
            return "redirect:/lecturer/grades?deleted=true";
        } catch (IllegalArgumentException ex) {
            return "redirect:/lecturer/grades?error=" + ex.getMessage().replace(" ", "+");
        }
    }

    /**
     * GET /lecturer/students - View enrolled students
     * (In a full implementation, would show students enrolled in lecturer's courses)
     */
    @GetMapping("/students")
    public String viewStudents(Authentication authentication, Model model) {
        User currentUser = (User) authentication.getPrincipal();
        Optional<Lecturer> lecturer = lecturerService.getLecturerByUserId(currentUser.getId());

        if (lecturer.isPresent()) {
            model.addAttribute("lecturer", lecturer.get());
        }
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("enrollments", enrollmentService.getAllEnrollments());

        return "lecturer/students";
    }

    private Optional<Lecturer> getCurrentLecturer(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return lecturerService.getLecturerByUserId(currentUser.getId());
    }
}
