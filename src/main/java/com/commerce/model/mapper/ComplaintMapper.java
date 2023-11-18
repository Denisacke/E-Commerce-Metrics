package com.commerce.model.mapper;

import com.commerce.model.Complaint;
import com.commerce.model.Customer;
import com.commerce.model.dto.ComplaintDTO;

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
