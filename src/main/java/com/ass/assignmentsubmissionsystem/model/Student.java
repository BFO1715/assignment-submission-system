package com.ass.assignmentsubmissionsystem.model;

public class Student {

    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String courseId;

    public Student() {}

    public Student(String studentId, String firstName, String lastName,
                   String email, String password, String courseId) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.password  = password;
        this.courseId  = courseId;
    }

    public String getStudentId()  { return studentId; }
    public String getFirstName()  { return firstName; }
    public String getPassword() { return password; }
    public String getLastName()   { return lastName; }
    public String getEmail()      { return email; }
    public String getCourseId()   { return courseId; }
    public String getFullName()   { return firstName + " " + lastName; }

    public void setStudentId(String studentId)  { this.studentId = studentId; }
    public void setFirstName(String firstName)  { this.firstName = firstName; }
    public void setLastName(String lastName)    { this.lastName = lastName; }
    public void setEmail(String email)          { this.email = email; }
    public void setPassword(String password)    { this.password = password; }
    public void setCourseId(String courseId)    { this.courseId = courseId; }
}
