package com.commerce.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity{

    @Column
    private String username;

    @Column(name = "password")
    private String password;

    @Column
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Complaint> complaints;

    public Customer(){

    }

    public Customer(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
