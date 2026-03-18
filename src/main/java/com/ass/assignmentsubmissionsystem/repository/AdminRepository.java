package com.ass.assignmentsubmissionsystem.repository;

import com.ass.assignmentsubmissionsystem.model.Administrator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepository {

    private final JdbcTemplate jdbcTemplate;

    public AdminRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Administrator> adminRowMapper = (rs, rowNum) -> {
        Administrator a = new Administrator();
        a.setAdminId(rs.getString("adminId"));
        a.setFirstName(rs.getString("firstName"));
        a.setLastName(rs.getString("lastName"));
        a.setEmail(rs.getString("email"));
        a.setPassword(rs.getString("password"));
        return a;
    };

    public int save(Administrator administrator) {
        String sql = "INSERT INTO administrator (adminId, firstName, lastName, email, password) " +
                     "VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                administrator.getAdminId(),
                administrator.getFirstName(),
                administrator.getLastName(),
                administrator.getEmail(),
                administrator.getPassword()
        );
    }

    public Optional<Administrator> findByEmail(String email) {
        String sql = "SELECT * FROM administrator WHERE email = ?";
        List<Administrator> results = jdbcTemplate.query(sql, adminRowMapper, email);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<Administrator> findById(String adminId) {
        String sql = "SELECT * FROM administrator WHERE adminId = ?";
        List<Administrator> results = jdbcTemplate.query(sql, adminRowMapper, adminId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
