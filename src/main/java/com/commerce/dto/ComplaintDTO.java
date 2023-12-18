package com.commerce.dto;

import com.commerce.constant.ComplaintType;

public class ComplaintDTO {

    private ComplaintType complaintType;

    private String description;

    private String status;

    private String customerName;

    public ComplaintDTO(){

    }

    public ComplaintDTO(ComplaintType complaintType, String description, String status, String customerName) {
        this.complaintType = complaintType;
        this.description = description;
        this.status = status;
        this.customerName = customerName;
    }

    public ComplaintType getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(ComplaintType complaintType) {
        this.complaintType = complaintType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "ComplaintDTO{" +
                "complaintType=" + complaintType +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
