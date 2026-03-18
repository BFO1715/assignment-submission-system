package com.ass.assignmentsubmissionsystem.security;

import com.ass.assignmentsubmissionsystem.repository.StudentRepository;
import com.ass.assignmentsubmissionsystem.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    public AppUserDetailsService(StudentRepository studentRepository,
                                 AdminRepository adminRepository) {
        this.studentRepository = studentRepository;
        this.adminRepository   = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Check administrator table first
        var admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return User.builder()
                    .username(admin.get().getEmail())
                    .password(admin.get().getPassword())
                    .roles("ADMIN")
                    .build();
        }

        // Then check student table
        return studentRepository.findByEmail(email)
                .map(student -> User.builder()
                        .username(student.getEmail())
                        .password(student.getPassword())
                        .roles("STUDENT")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
    }
}
