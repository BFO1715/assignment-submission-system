package com.ass.assignmentsubmissionsystem.model;

public class Submission {

    private String submissionId;
    private String studentId;
    private String assignmentId;
    private String fileName;
    private String fileType;
    private double fileSizeMB;
    private String caseReference;
    private String status;
    private String additionalInfo;
    private String submittedAt;
    private String updatedAt;

    public Submission() {}

    public Submission(String submissionId, String studentId, String assignmentId,
                      String fileName, String fileType, double fileSizeMB,
                      String caseReference, String status, String additionalInfo) {
        this.submissionId   = submissionId;
        this.studentId      = studentId;
        this.assignmentId   = assignmentId;
        this.fileName       = fileName;
        this.fileType       = fileType;
        this.fileSizeMB     = fileSizeMB;
        this.caseReference  = caseReference;
        this.status         = status;
        this.additionalInfo = additionalInfo;
    }

    public String getSubmissionId()   { return submissionId; }
    public String getStudentId()      { return studentId; }
    public String getAssignmentId()   { return assignmentId; }
    public String getFileName()       { return fileName; }
    public String getFileType()       { return fileType; }
    public double getFileSizeMB()     { return fileSizeMB; }
    public String getCaseReference()  { return caseReference; }
    public String getStatus()         { return status; }
    public String getAdditionalInfo() { return additionalInfo; }
    public String getSubmittedAt()    { return submittedAt; }
    public String getUpdatedAt()      { return updatedAt; }

    public void setSubmissionId(String submissionId)     { this.submissionId = submissionId; }
    public void setStudentId(String studentId)           { this.studentId = studentId; }
    public void setAssignmentId(String assignmentId)     { this.assignmentId = assignmentId; }
    public void setFileName(String fileName)             { this.fileName = fileName; }
    public void setFileType(String fileType)             { this.fileType = fileType; }
    public void setFileSizeMB(double fileSizeMB)         { this.fileSizeMB = fileSizeMB; }
    public void setCaseReference(String caseReference)   { this.caseReference = caseReference; }
    public void setStatus(String status)                 { this.status = status; }
    public void setAdditionalInfo(String additionalInfo) { this.additionalInfo = additionalInfo; }
    public void setSubmittedAt(String submittedAt)       { this.submittedAt = submittedAt; }
    public void setUpdatedAt(String updatedAt)           { this.updatedAt = updatedAt; }
}
