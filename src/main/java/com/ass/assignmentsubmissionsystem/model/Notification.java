package com.ass.assignmentsubmissionsystem.model;

public class Notification {

    private String notificationId;
    private String recipientId;
    private String recipientType;
    private String message;
    private boolean isRead;
    private String createdAt;

    public Notification() {}

    public Notification(String notificationId, String recipientId,
                        String recipientType, String message) {
        this.notificationId = notificationId;
        this.recipientId    = recipientId;
        this.recipientType  = recipientType;
        this.message        = message;
        this.isRead         = false;
    }

    public String getNotificationId()  { return notificationId; }
    public String getRecipientId()     { return recipientId; }
    public String getRecipientType()   { return recipientType; }
    public String getMessage()         { return message; }
    public boolean isRead()            { return isRead; }
    public String getCreatedAt()       { return createdAt; }

    public void setNotificationId(String notificationId) { this.notificationId = notificationId; }
    public void setRecipientId(String recipientId)       { this.recipientId = recipientId; }
    public void setRecipientType(String recipientType)   { this.recipientType = recipientType; }
    public void setMessage(String message)               { this.message = message; }
    public void setRead(boolean isRead)                  { this.isRead = isRead; }
    public void setCreatedAt(String createdAt)           { this.createdAt = createdAt; }
}
