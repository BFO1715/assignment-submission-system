package com.ass.assignmentsubmissionsystem.model;

public class Administrator {

    private String adminId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Administrator() {}

    public Administrator(String adminId, String firstName, String lastName,
                         String email, String password) {
        this.adminId   = adminId;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.password  = password;
    }

    public String getAdminId()   { return adminId; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getEmail()     { return email; }
    public String getPassword()  { return password; }
    public String getFullName()  { return firstName + " " + lastName; }

    public void setAdminId(String adminId)     { this.adminId = adminId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName = lastName; }
    public void setEmail(String email)         { this.email = email; }
    public void setPassword(String password)   { this.password = password; }
}
