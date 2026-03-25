package com.ass.assignmentsubmissionsystem.model;

public class User {

    private String email;
    private String password;

    public User() {}

    public User(String email, String password) {
        this.email    = email;
        this.password = password;
    }

    public String getEmail()    { return email; }
    public String getPassword() { return password; }

    public void setEmail(String email)       { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public boolean checkPassword(String password) {
        if (password == null) return false;
        if (password.length() < 6 || password.length() > 10) return false;
        if (!Character.isLetter(password.charAt(0)) ||
            !Character.isLetter(password.charAt(1)) ||
            !Character.isLetter(password.charAt(2))) return false;
        return true;
    }
}
