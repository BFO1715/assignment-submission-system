package com.ass.assignmentsubmissionsystem.repository;

import com.ass.assignmentsubmissionsystem.model.Notification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class NotificationRepository {

    private final JdbcTemplate jdbcTemplate;

    public NotificationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Notification> notificationRowMapper = (rs, rowNum) -> {
        Notification n = new Notification();
        n.setNotificationId(rs.getString("notificationId"));
        n.setRecipientId(rs.getString("recipientId"));
        n.setRecipientType(rs.getString("recipientType"));
        n.setMessage(rs.getString("message"));
        n.setRead(rs.getBoolean("isRead"));
        n.setCreatedAt(rs.getString("createdAt"));
        return n;
    };

    public int save(Notification notification) {
        String sql = "INSERT INTO notification (notificationId, recipientId, recipientType, message) " +
                     "VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                notification.getNotificationId(),
                notification.getRecipientId(),
                notification.getRecipientType(),
                notification.getMessage()
        );
    }

    public List<Notification> findByRecipientId(String recipientId) {
        String sql = "SELECT * FROM notification WHERE recipientId = ? ORDER BY createdAt DESC";
        return jdbcTemplate.query(sql, notificationRowMapper, recipientId);
    }

    public int markAsRead(String notificationId) {
        String sql = "UPDATE notification SET isRead = TRUE WHERE notificationId = ?";
        return jdbcTemplate.update(sql, notificationId);
    }
}
