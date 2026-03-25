package com.ass.assignmentsubmissionsystem.controller;

import com.ass.assignmentsubmissionsystem.model.Assignment;
import com.ass.assignmentsubmissionsystem.model.Student;
import com.ass.assignmentsubmissionsystem.model.Submission;
import com.ass.assignmentsubmissionsystem.model.Notification;
import com.ass.assignmentsubmissionsystem.repository.AssignmentRepository;
import com.ass.assignmentsubmissionsystem.repository.StudentRepository;
import com.ass.assignmentsubmissionsystem.repository.SubmissionRepository;
import com.ass.assignmentsubmissionsystem.repository.NotificationRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final NotificationRepository notificationRepository;

    public StudentController(StudentRepository studentRepository,
                             AssignmentRepository assignmentRepository,
                             SubmissionRepository submissionRepository,
                             NotificationRepository notificationRepository) {
        this.studentRepository      = studentRepository;
        this.assignmentRepository   = assignmentRepository;
        this.submissionRepository   = submissionRepository;
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Optional<Student> student = studentRepository.findByEmail(userDetails.getUsername());
        student.ifPresent(s -> {
            String studentCourseId = s.getCourseId().trim();

            // Filter assignments where student's courseId appears in assignment's
            // comma separated courseId list, and studentId matches or is blank
            List<Assignment> assignments = assignmentRepository.findAll().stream()
                    .filter(a -> Arrays.stream(a.getCourseId().split(","))
                            .map(String::trim)
                            .collect(Collectors.toList())
                            .contains(studentCourseId))
                    .filter(a -> a.getStudentId() == null ||
                                 a.getStudentId().isEmpty() ||
                                 a.getStudentId().equals(s.getStudentId()))
                    .collect(Collectors.toList());

            model.addAttribute("student", s);
            model.addAttribute("assignments", assignments);
            model.addAttribute("submissions", submissionRepository.findByStudentId(s.getStudentId()));
            model.addAttribute("notifications", notificationRepository.findByRecipientId(s.getStudentId()));
        });
        return "student-dashboard";
    }

    @GetMapping("/submit/{assignmentId}")
    public String showSubmitForm(@PathVariable String assignmentId, Model model) {
        model.addAttribute("submission", new Submission());
        model.addAttribute("assignmentId", assignmentId);
        assignmentRepository.findById(assignmentId)
                .ifPresent(a -> model.addAttribute("assignment", a));
        return "submit-assignment";
    }

    @PostMapping("/submit")
    public String processSubmit(@ModelAttribute Submission submission,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        Optional<Student> student = studentRepository.findByEmail(userDetails.getUsername());
        if (student.isPresent()) {
            String submissionId  = "SUB-" + System.currentTimeMillis();
            String caseReference = "CASE-" + System.currentTimeMillis();
            submission.setSubmissionId(submissionId);
            submission.setStudentId(student.get().getStudentId());
            submission.setCaseReference(caseReference);

            // Check if submission is late
            String status = "SUBMITTED";
            Optional<Assignment> assignment = assignmentRepository.findById(submission.getAssignmentId());
            if (assignment.isPresent()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime deadline = LocalDateTime.parse(assignment.get().getDeadline(), formatter);
                    if (LocalDateTime.now().isAfter(deadline)) {
                        status = "LATE";
                    }
                } catch (Exception e) {
                    // If deadline parsing fails default to SUBMITTED
                }
            }
            submission.setStatus(status);
            submissionRepository.save(submission);

            String notifMessage = status.equals("LATE")
                ? "Your assignment was submitted late. Case reference: " + caseReference
                : "Your assignment has been submitted successfully. Case reference: " + caseReference;

            Notification notification = new Notification(
                "NOTIF-" + System.currentTimeMillis(),
                student.get().getStudentId(),
                "STUDENT",
                notifMessage
            );
            notificationRepository.save(notification);

            model.addAttribute("caseReference", caseReference);
            model.addAttribute("submission", submission);
            return "receipt";
        }
        return "redirect:/student/dashboard";
    }
}
