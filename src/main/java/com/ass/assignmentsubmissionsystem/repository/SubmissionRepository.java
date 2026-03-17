package com.ass.assignmentsubmissionsystem.repository;

import com.ass.assignmentsubmissionsystem.model.Submission;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class SubmissionRepository {

    private final JdbcTemplate jdbcTemplate;

    public SubmissionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Submission> submissionRowMapper = (rs, rowNum) -> {
        Submission s = new Submission();
        s.setSubmissionId(rs.getString("submissionId"));
        s.setStudentId(rs.getString("studentId"));
        s.setAssignmentId(rs.getString("assignmentId"));
        s.setFileName(rs.getString("fileName"));
        s.setFileType(rs.getString("fileType"));
        s.setFileSizeMB(rs.getDouble("fileSizeMB"));
        s.setCaseReference(rs.getString("caseReference"));
        s.setStatus(rs.getString("status"));
        s.setAdditionalInfo(rs.getString("additionalInfo"));
        s.setSubmittedAt(rs.getString("submittedAt"));
        s.setUpdatedAt(rs.getString("updatedAt"));
        return s;
    };

    public int save(Submission submission) {
        String sql = "INSERT INTO submission (submissionId, studentId, assignmentId, fileName, " +
                     "fileType, fileSizeMB, caseReference, status, additionalInfo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                submission.getSubmissionId(),
                submission.getStudentId(),
                submission.getAssignmentId(),
                submission.getFileName(),
                submission.getFileType(),
                submission.getFileSizeMB(),
                submission.getCaseReference(),
                submission.getStatus(),
                submission.getAdditionalInfo()
        );
    }

    public List<Submission> findByStudentId(String studentId) {
        String sql = "SELECT * FROM submission WHERE studentId = ? ORDER BY submittedAt DESC";
        return jdbcTemplate.query(sql, submissionRowMapper, studentId);
    }

    public List<Submission> findAll() {
        String sql = "SELECT * FROM submission ORDER BY submittedAt DESC";
        return jdbcTemplate.query(sql, submissionRowMapper);
    }

    public Optional<Submission> findById(String submissionId) {
        String sql = "SELECT * FROM submission WHERE submissionId = ?";
        List<Submission> results = jdbcTemplate.query(sql, submissionRowMapper, submissionId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int update(Submission submission) {
        String sql = "UPDATE submission SET fileName = ?, fileType = ?, fileSizeMB = ?, " +
                     "additionalInfo = ?, status = ? WHERE submissionId = ?";
        return jdbcTemplate.update(sql,
                submission.getFileName(),
                submission.getFileType(),
                submission.getFileSizeMB(),
                submission.getAdditionalInfo(),
                submission.getStatus(),
                submission.getSubmissionId()
        );
    }

    public int delete(String submissionId) {
        String sql = "DELETE FROM submission WHERE submissionId = ?";
        return jdbcTemplate.update(sql, submissionId);
    }
}
