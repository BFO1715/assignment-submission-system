package com.ass.assignmentsubmissionsystem.controller;

import com.ass.assignmentsubmissionsystem.model.Assignment;
import com.ass.assignmentsubmissionsystem.repository.AssignmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;

    public AssignmentController(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping("/create-assignment")
    public String showForm(Model model) {
        model.addAttribute("assignment", new Assignment());
        return "create-assignment";
    }

    @PostMapping("/create-assignment")
    public String submitForm(@ModelAttribute Assignment assignment, Model model) {
        assignmentRepository.save(assignment);
        model.addAttribute("successMessage", "Assignment created successfully!");
        model.addAttribute("assignment", new Assignment());
        return "create-assignment";
    }
}
