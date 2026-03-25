package com.ass.assignmentsubmissionsystem.controller;

import com.ass.assignmentsubmissionsystem.model.Assignment;
import com.ass.assignmentsubmissionsystem.model.Submission;
import com.ass.assignmentsubmissionsystem.model.Notification;
import com.ass.assignmentsubmissionsystem.model.Student;
import com.ass.assignmentsubmissionsystem.repository.AssignmentRepository;
import com.ass.assignmentsubmissionsystem.repository.SubmissionRepository;
import com.ass.assignmentsubmissionsystem.repository.NotificationRepository;
import com.ass.assignmentsubmissionsystem.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(SubmissionRepository submissionRepository,
                           NotificationRepository notificationRepository,
                           AssignmentRepository assignmentRepository,
                           StudentRepository studentRepository,
                           PasswordEncoder passwordEncoder) {
        this.submissionRepository   = submissionRepository;
        this.notificationRepository = notificationRepository;
        this.assignmentRepository   = assignmentRepository;
        this.studentRepository      = studentRepository;
        this.passwordEncoder        = passwordEncoder;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("submissions", submissionRepository.findAll());
        model.addAttribute("assignments", assignmentRepository.findAll());
        model.addAttribute("students", studentRepository.findAll());
        return "admin-dashboard";
    }

    // ── Submission CRUD ──

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

    // ── Assignment CRUD ──

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

    // ── Student password reset ──

    @GetMapping("/reset-password/{studentId}")
    public String showResetPassword(@PathVariable String studentId, Model model) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "reset-password";
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String studentId,
                                       @RequestParam String newPassword,
                                       Model model) {
        if (newPassword.length() < 6 || newPassword.length() > 10) {
            Optional<Student> student = studentRepository.findById(studentId);
            student.ifPresent(s -> model.addAttribute("student", s));
            model.addAttribute("errorMessage", "Password must be between 6 and 10 characters.");
            return "reset-password";
        }
        if (!Character.isLetter(newPassword.charAt(0)) ||
            !Character.isLetter(newPassword.charAt(1)) ||
            !Character.isLetter(newPassword.charAt(2))) {
            Optional<Student> student = studentRepository.findById(studentId);
            student.ifPresent(s -> model.addAttribute("student", s));
            model.addAttribute("errorMessage", "The first 3 characters of the password must be letters.");
            return "reset-password";
        }
        studentRepository.updatePassword(studentId, passwordEncoder.encode(newPassword));
        Notification notification = new Notification(
            "NOTIF-" + System.currentTimeMillis(),
            studentId,
            "STUDENT",
            "Your password has been reset by the administrator. Please log in with your new password."
        );
        notificationRepository.save(notification);
        return "redirect:/admin/dashboard?passwordReset";
    }
}
