package com.ass.assignmentsubmissionsystem.controller;

import com.ass.assignmentsubmissionsystem.model.Submission;
import com.ass.assignmentsubmissionsystem.model.Notification;
import com.ass.assignmentsubmissionsystem.repository.SubmissionRepository;
import com.ass.assignmentsubmissionsystem.repository.NotificationRepository;
import com.ass.assignmentsubmissionsystem.repository.AssignmentRepository;
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

    @GetMapping("/edit/{submissionId}")
    public String showEditForm(@PathVariable String submissionId, Model model) {
        Optional<Submission> submission = submissionRepository.findById(submissionId);
        submission.ifPresent(s -> model.addAttribute("submission", s));
        return "edit-submission";
    }

    @PostMapping("/edit")
    public String processEdit(@ModelAttribute Submission submission, Model model) {
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
}
