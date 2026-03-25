package com.ass.assignmentsubmissionsystem.controller;

import com.ass.assignmentsubmissionsystem.model.Student;
import com.ass.assignmentsubmissionsystem.model.Administrator;
import com.ass.assignmentsubmissionsystem.model.User;
import com.ass.assignmentsubmissionsystem.repository.StudentRepository;
import com.ass.assignmentsubmissionsystem.repository.AdminRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(StudentRepository studentRepository,
                          AdminRepository adminRepository,
                          PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.adminRepository   = adminRepository;
        this.passwordEncoder   = passwordEncoder;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute Student student, Model model) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            model.addAttribute("errorMessage", "An account with this email already exists.");
            model.addAttribute("student", student);
            return "register";
        }
        String password = student.getPassword();
        if (!new User().checkPassword(password)) {
            model.addAttribute("errorMessage",
                "Password must be 6-10 characters and the first 3 characters must be letters.");
            model.addAttribute("student", student);
            return "register";
        }
        student.setStudentId("STU-" + System.currentTimeMillis());
        student.setPassword(passwordEncoder.encode(password));
        studentRepository.save(student);
        return "redirect:/login?registered";
    }

    @GetMapping("/admin-register")
    public String showAdminRegister(Model model) {
        model.addAttribute("administrator", new Administrator());
        return "admin-register";
    }

    @PostMapping("/admin-register")
    public String processAdminRegister(@ModelAttribute Administrator administrator, Model model) {
        String password = administrator.getPassword();
        if (!new User().checkPassword(password)) {
            model.addAttribute("errorMessage",
                "Password must be 6-10 characters and the first 3 characters must be letters.");
            model.addAttribute("administrator", administrator);
            return "admin-register";
        }
        administrator.setAdminId("ADM-" + System.currentTimeMillis());
        administrator.setPassword(passwordEncoder.encode(password));
        adminRepository.save(administrator);
        return "redirect:/login?registered";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/student/dashboard";
    }
}
