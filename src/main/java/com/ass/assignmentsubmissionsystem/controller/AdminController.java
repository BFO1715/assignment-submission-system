package com.ass.assignmentsubmissionsystem.controller;

import com.ass.assignmentsubmissionsystem.model.Assignment;
import com.ass.assignmentsubmissionsystem.model.Submission;
import com.ass.assignmentsubmissionsystem.model.Notification;
import com.ass.assignmentsubmissionsystem.repository.AssignmentRepository;
import com.ass.assignmentsubmissionsystem.repository.SubmissionRepository;
import com.ass.assignmentsubmissionsystem.repository.NotificationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SubmissionRepository submissionRepository;
    private final NotificationRepository notificationRepository;
    private final AssignmentRepository assignmentRepository;

    public AdminController(SubmissionRepository submissionRepository,
                           NotificationRepository notificationRepository,
                           AssignmentRepository assignmentRepository) {
        this.submissionRepository   = submissionRepository;
        this.notificationRepository = notificationRepository;
        this.assignmentRepository   = assignmentRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("submissions", submissionRepository.findAll());
        model.addAttribute("assignments", assignmentRepository.findAll());
        return "admin-dashboard";
    }

    // Submission CRUD

    @GetMapping("/edit/{submissionId}")
    public String showEditForm(@PathVariable String submissionId, Model model) {
        Optional<Submission> submission = submissionRepository.findById(submissionId);
        if (submission.isPresent()) {
            model.addAttribute("submission", submission.get());
            return "edit-submission";
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/edit")
    public String processEdit(@ModelAttribute Submission submission) {
        submissionRepository.update(submission);
        Notification notification = new Notification(
            "NOTIF-" + System.currentTimeMillis(),
            submission.getStudentId(),
            "STUDENT",
            "Your submission has been updated by the administrator."
        );
        notificationRepository.save(notification);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete/{submissionId}")
    public String deleteSubmission(@PathVariable String submissionId) {
        submissionRepository.delete(submissionId);
        return "redirect:/admin/dashboard";
    }

    // Assignment CRUD

    @GetMapping("/edit-assignment/{assignmentId}")
    public String showEditAssignmentForm(@PathVariable String assignmentId, Model model) {
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        if (assignment.isPresent()) {
            model.addAttribute("assignment", assignment.get());
            return "edit-assignment";
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/edit-assignment")
    public String processEditAssignment(@ModelAttribute Assignment assignment) {
        assignmentRepository.update(assignment);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete-assignment/{assignmentId}")
    public String deleteAssignment(@PathVariable String assignmentId) {
        assignmentRepository.delete(assignmentId);
        return "redirect:/admin/dashboard";
    }
}
