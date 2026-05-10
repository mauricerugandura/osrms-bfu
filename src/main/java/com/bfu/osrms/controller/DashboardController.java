package com.bfu.osrms.controller;

import com.bfu.osrms.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * CONTROLLER LAYER - DashboardController
 * Handles main dashboard redirect based on user role
 * MVC Pattern: Routes authenticated users to role-specific dashboards
 */
@Controller
public class DashboardController {

    /**
     * GET / - Home page redirect
     */
    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    /**
     * GET /dashboard - Main dashboard, redirects to role-specific dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        User currentUser = (User) authentication.getPrincipal();
        model.addAttribute("user", currentUser);

        return switch (currentUser.getRole().name()) {
            case "ADMIN" -> "redirect:/admin/dashboard";
            case "LECTURER" -> "redirect:/lecturer/dashboard";
            case "STUDENT" -> "redirect:/student/dashboard";
            case "REGISTRAR" -> "redirect:/registrar/dashboard";
            default -> "redirect:/login";
        };
    }
}
