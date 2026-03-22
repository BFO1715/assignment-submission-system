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

import java.util.List;
import java.util.Optional;

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
            model.addAttribute("student", s);
            model.addAttribute("submissions", submissionRepository.findByStudentId(s.getStudentId()));
            model.addAttribute("notifications", notificationRepository.findByRecipientId(s.getStudentId()));
            model.addAttribute("assignments", assignmentRepository.findByCourseId(s.getCourseId()));
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
            submission.setStatus("SUBMITTED");
            submissionRepository.save(submission);

            Notification notification = new Notification(
                "NOTIF-" + System.currentTimeMillis(),
                student.get().getStudentId(),
                "STUDENT",
                "Your assignment has been submitted successfully. Case reference: " + caseReference
            );
            notificationRepository.save(notification);

            model.addAttribute("caseReference", caseReference);
            model.addAttribute("submission", submission);
            return "receipt";
        }
        return "redirect:/student/dashboard";
    }
}
