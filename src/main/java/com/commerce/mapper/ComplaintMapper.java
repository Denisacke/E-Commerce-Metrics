package com.commerce.mapper;

import com.commerce.model.Complaint;
import com.commerce.model.Customer;
import com.commerce.dto.ComplaintDTO;

public class ComplaintMapper {

    private ComplaintMapper(){

    }

    public static Complaint mapComplaintDTOToComplaint(ComplaintDTO complaintDTO, Customer customer){
        return new Complaint(complaintDTO.getComplaintType(),
                complaintDTO.getDescription(),
                complaintDTO.getStatus(),
                customer
        );
    }
}
