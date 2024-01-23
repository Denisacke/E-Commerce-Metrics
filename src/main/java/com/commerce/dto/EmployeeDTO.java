package com.commerce.dto;

/**
 * DTO class for representing Employee entities in the frontend
 */
public class EmployeeDTO {

    private String username;

    private String password;

    private String email;

    private Boolean isAdmin;

    public EmployeeDTO(){

    }

    public EmployeeDTO(String username, String password, String email, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
