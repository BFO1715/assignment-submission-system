package com.ass.assignmentsubmissionsystem.model;

public class Assignment {

    private String assignmentId;
    private String studentId;
    private String adminId;
    private String title;
    private String courseId;
    private String deadline;
    private int maxFileSizeMB;
    private String permittedFileTypes;
    private String additionalInfo;
    private String status;

    public Assignment() {}

    public Assignment(String assignmentId, String studentId, String adminId,
                      String title, String courseId, String deadline,
                      int maxFileSizeMB, String permittedFileTypes,
                      String additionalInfo, String status) {
        this.assignmentId     = assignmentId;
        this.studentId        = studentId;
        this.adminId          = adminId;
        this.title            = title;
        this.courseId         = courseId;
        this.deadline         = deadline;
        this.maxFileSizeMB    = maxFileSizeMB;
        this.permittedFileTypes = permittedFileTypes;
        this.additionalInfo   = additionalInfo;
        this.status           = status;
    }

    public String getAssignmentId()       { return assignmentId; }
    public String getStudentId()          { return studentId; }
    public String getAdminId()            { return adminId; }
    public String getTitle()              { return title; }
    public String getCourseId()           { return courseId; }
    public String getDeadline()           { return deadline; }
    public int getMaxFileSizeMB()         { return maxFileSizeMB; }
    public String getPermittedFileTypes() { return permittedFileTypes; }
    public String getAdditionalInfo()     { return additionalInfo; }
    public String getStatus()             { return status; }

    public void setAssignmentId(String assignmentId)         { this.assignmentId = assignmentId; }
    public void setStudentId(String studentId)               { this.studentId = studentId; }
    public void setAdminId(String adminId)                   { this.adminId = adminId; }
    public void setTitle(String title)                       { this.title = title; }
    public void setCourseId(String courseId)                 { this.courseId = courseId; }
    public void setDeadline(String deadline)                 { this.deadline = deadline; }
    public void setMaxFileSizeMB(int maxFileSizeMB)          { this.maxFileSizeMB = maxFileSizeMB; }
    public void setPermittedFileTypes(String permittedFileTypes) { this.permittedFileTypes = permittedFileTypes; }
    public void setAdditionalInfo(String additionalInfo)     { this.additionalInfo = additionalInfo; }
    public void setStatus(String status)                     { this.status = status; }
}
