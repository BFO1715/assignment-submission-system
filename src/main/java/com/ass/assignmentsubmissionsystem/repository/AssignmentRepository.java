package com.ass.assignmentsubmissionsystem.repository;

import com.ass.assignmentsubmissionsystem.model.Assignment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AssignmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public AssignmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Assignment assignment) {
        String sql = "INSERT INTO assignment (assignmentId, studentId, adminId, title, courseId, " +
                     "deadline, maxFileSizeMB, permittedFileTypes, additionalInfo, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                assignment.getAssignmentId(),
                assignment.getStudentId(),
                assignment.getAdminId(),
                assignment.getTitle(),
                assignment.getCourseId(),
                assignment.getDeadline(),
                assignment.getMaxFileSizeMB(),
                assignment.getPermittedFileTypes(),
                assignment.getAdditionalInfo(),
                assignment.getStatus()
        );
    }
}