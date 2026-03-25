package com.ass.assignmentsubmissionsystem.model;

public class Student extends User {

    private String studentId;
    private String firstName;
    private String lastName;
    private String courseId;

    public Student() {}

    public Student(String studentId, String firstName, String lastName,
                   String email, String password, String courseId) {
        super(email, password);
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.courseId  = courseId;
    }

    public String getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getCourseId()  { return courseId; }
    public String getFullName()  { return firstName + " " + lastName; }

    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }
    public void setCourseId(String courseId)   { this.courseId = courseId; }
}
