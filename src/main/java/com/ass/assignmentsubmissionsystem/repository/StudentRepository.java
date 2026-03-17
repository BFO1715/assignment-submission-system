package com.ass.assignmentsubmissionsystem.repository;

import com.ass.assignmentsubmissionsystem.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Student> studentRowMapper = (rs, rowNum) -> {
        Student s = new Student();
        s.setStudentId(rs.getString("studentId"));
        s.setFirstName(rs.getString("firstName"));
        s.setLastName(rs.getString("lastName"));
        s.setEmail(rs.getString("email"));
        s.setPassword(rs.getString("password"));
        s.setCourseId(rs.getString("courseId"));
        return s;
    };

    public int save(Student student) {
        String sql = "INSERT INTO student (studentId, firstName, lastName, email, password, courseId) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPassword(),
                student.getCourseId()
        );
    }

    public Optional<Student> findByEmail(String email) {
        String sql = "SELECT * FROM student WHERE email = ?";
        List<Student> results = jdbcTemplate.query(sql, studentRowMapper, email);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<Student> findById(String studentId) {
        String sql = "SELECT * FROM student WHERE studentId = ?";
        List<Student> results = jdbcTemplate.query(sql, studentRowMapper, studentId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM student WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
