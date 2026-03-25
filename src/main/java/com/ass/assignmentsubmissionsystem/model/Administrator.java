package com.ass.assignmentsubmissionsystem.model;

public class Administrator extends User {

    private String adminId;
    private String firstName;
    private String lastName;

    public Administrator() {}

    public Administrator(String adminId, String firstName, String lastName,
                         String email, String password) {
        super(email, password);
        this.adminId   = adminId;
        this.firstName = firstName;
        this.lastName  = lastName;
    }

    public String getAdminId()   { return adminId; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getFullName()  { return firstName + " " + lastName; }

    public void setAdminId(String adminId)     { this.adminId = adminId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }
}
