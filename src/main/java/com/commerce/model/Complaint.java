package com.commerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "complaint")
public class Complaint extends AbstractEntity{

    @Column
    private String type;

    @Column
    private String description;

    @Column
    private String status;

    @Column
    private int id_customer;

    public Complaint(){

    }

    public Complaint(String type, String description, String status, int id_customer){
        this.type = type;
        this.description = description;
        this.status = status;
        this.id_customer = id_customer;
    }

    public String getStatus() {
        return status;
    }

    public int getId_customer() {
        return id_customer;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id_customer=" + id_customer +
                '}';
    }
}
