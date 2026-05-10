package com.bfu.osrms.controller;

import com.bfu.osrms.model.User;
import com.bfu.osrms.model.UserRole;
import com.bfu.osrms.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * CONTROLLER LAYER - AuthController
 * Handles user authentication: login and logout
 * MVC Pattern: Controller receives requests and delegates to services
 */
@Controller
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * GET /login - Display login page
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * POST /login - Form login (handled by Spring Security)
     * This mapping is processed by SecurityConfig
     */

    /**
     * GET /logout - Handle logout
     * Logout is handled by Spring Security configuration
     */

    /**
     * GET /register - Display registration page
     */
    @GetMapping("/register")
    public String registerPage() {
        return "redirect:/login?adminRegistration=true";
    }

    /**
     * POST /register - Register new user
     */
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        return "redirect:/login?adminRegistration=true";
    }
}
