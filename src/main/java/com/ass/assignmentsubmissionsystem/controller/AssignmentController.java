package com.ass.assignmentsubmissionsystem.controller;

import com.ass.assignmentsubmissionsystem.model.Assignment;
import com.ass.assignmentsubmissionsystem.repository.AssignmentRepository;
import com.ass.assignmentsubmissionsystem.repository.AdminRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;
    private final AdminRepository adminRepository;

    public AssignmentController(AssignmentRepository assignmentRepository,
                                AdminRepository adminRepository) {
        this.assignmentRepository = assignmentRepository;
        this.adminRepository      = adminRepository;
    }

    @GetMapping("/create-assignment")
    public String showForm(Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("assignment", new Assignment());
        adminRepository.findByEmail(userDetails.getUsername())
                .ifPresent(a -> model.addAttribute("adminId", a.getAdminId()));
        return "create-assignment";
    }

    @PostMapping("/create-assignment")
    public String submitForm(@ModelAttribute Assignment assignment,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model) {
        assignment.setStatus("SUBMITTED");
        assignmentRepository.save(assignment);
        model.addAttribute("successMessage", "Assignment created successfully!");
        model.addAttribute("assignment", new Assignment());
        adminRepository.findByEmail(userDetails.getUsername())
                .ifPresent(a -> model.addAttribute("adminId", a.getAdminId()));
        return "create-assignment";
    }
}