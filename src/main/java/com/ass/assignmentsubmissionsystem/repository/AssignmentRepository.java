package com.ass.assignmentsubmissionsystem.repository;

import com.ass.assignmentsubmissionsystem.model.Assignment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class AssignmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public AssignmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Assignment> assignmentRowMapper = (rs, rowNum) -> {
        Assignment a = new Assignment();
        a.setAssignmentId(rs.getString("assignmentId"));
        a.setStudentId(rs.getString("studentId"));
        a.setAdminId(rs.getString("adminId"));
        a.setTitle(rs.getString("title"));
        a.setCourseId(rs.getString("courseId"));
        a.setDeadline(rs.getString("deadline"));
        a.setMaxFileSizeMB(rs.getInt("maxFileSizeMB"));
        a.setPermittedFileTypes(rs.getString("permittedFileTypes"));
        a.setAdditionalInfo(rs.getString("additionalInfo"));
        a.setStatus(rs.getString("status"));
        return a;
    };

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

    public List<Assignment> findAll() {
        String sql = "SELECT * FROM assignment ORDER BY createdAt DESC";
        return jdbcTemplate.query(sql, assignmentRowMapper);
    }

    public List<Assignment> findByCourseIdAndStudentId(String courseId, String studentId) {
        String sql = "SELECT * FROM assignment WHERE courseId = ? " +
                     "AND (studentId IS NULL OR studentId = ?) " +
                     "ORDER BY createdAt DESC";
        return jdbcTemplate.query(sql, assignmentRowMapper, courseId, studentId);
    }

    public Optional<Assignment> findById(String assignmentId) {
        String sql = "SELECT * FROM assignment WHERE assignmentId = ?";
        List<Assignment> results = jdbcTemplate.query(sql, assignmentRowMapper, assignmentId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int update(Assignment assignment) {
        String sql = "UPDATE assignment SET title = ?, courseId = ?, deadline = ?, " +
                     "maxFileSizeMB = ?, permittedFileTypes = ?, additionalInfo = ?, " +
                     "status = ? WHERE assignmentId = ?";
        return jdbcTemplate.update(sql,
                assignment.getTitle(),
                assignment.getCourseId(),
                assignment.getDeadline(),
                assignment.getMaxFileSizeMB(),
                assignment.getPermittedFileTypes(),
                assignment.getAdditionalInfo(),
                assignment.getStatus(),
                assignment.getAssignmentId()
        );
    }

    public int delete(String assignmentId) {
        String sql = "DELETE FROM assignment WHERE assignmentId = ?";
        return jdbcTemplate.update(sql, assignmentId);
    }
}