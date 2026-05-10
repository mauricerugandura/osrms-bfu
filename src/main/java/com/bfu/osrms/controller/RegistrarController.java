package com.bfu.osrms.controller;

import com.bfu.osrms.model.Result;
import com.bfu.osrms.service.ResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * CONTROLLER LAYER - RegistrarController
 * Handles registrar-specific endpoints: dashboard, review, and result approval
 * MVC Pattern: Manages result approval and publication workflow
 */
@Controller
@RequestMapping("/registrar")
public class RegistrarController {

    private final ResultService resultService;

    public RegistrarController(ResultService resultService) {
        this.resultService = resultService;
    }

    /**
     * GET /registrar/dashboard - Display registrar dashboard
     */
    @GetMapping("/dashboard")
    public String registrarDashboard(Model model) {
        List<Result> pendingResults = resultService.getPendingResults();
        List<Result> approvedResults = resultService.getApprovedResults();
        model.addAttribute("pendingResultsCount", pendingResults.size());
        model.addAttribute("approvedResultsCount", approvedResults.size());
        return "registrar/dashboard";
    }

    /**
     * GET /registrar/review - Review pending results
     */
    @GetMapping("/review")
    public String reviewResults(Model model) {
        List<Result> pendingResults = resultService.getPendingResults();
        List<Result> approvedResults = resultService.getApprovedResults();
        model.addAttribute("results", pendingResults);
        model.addAttribute("approvedResults", approvedResults);
        return "registrar/review";
    }

    /**
     * POST /registrar/approve/{id} - Approve a result
     */
    @PostMapping("/approve/{id}")
    public String approveResult(@PathVariable Long id) {
        resultService.approveResult(id);
        return "redirect:/registrar/review?approved=true";
    }

    /**
     * POST /registrar/publish/{id} - Publish a result
     */
    @PostMapping("/publish/{id}")
    public String publishResult(@PathVariable Long id) {
        resultService.publishResult(id);
        return "redirect:/registrar/review?published=true";
    }

    /**
     * POST /registrar/publish-approved - Publish all approved results
     */
    @PostMapping("/publish-approved")
    public String publishApprovedResults() {
        int publishedCount = resultService.publishApprovedResults();
        return "redirect:/registrar/review?publishedCount=" + publishedCount;
    }
}
