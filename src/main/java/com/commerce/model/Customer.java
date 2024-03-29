package com.commerce.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity{

    @Column
    private String username;

    @Column(name = "password")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$*%^&+=])(?=\\S+$).{6,}$")
    private String password;

    @Column
    @Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Complaint> complaints;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Cart> carts;

    public Customer(){

    }

    public Customer(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
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
