package com.commerce.model;

import com.commerce.constant.ComplaintType;

import javax.persistence.*;

@Entity
@Table(name = "complaint")
public class Complaint extends AbstractEntity{

    @Enumerated(EnumType.STRING)
    @Column
    private ComplaintType type;

    @Column
    private String description;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Complaint(){

    }

    public Complaint(ComplaintType type, String description, String status, Customer customer){
        this.type = type;
        this.description = description;
        this.status = status;
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public ComplaintType getType() {
        return type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(ComplaintType type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id_customer=" + customer.getId() +
                '}';
    }
}
