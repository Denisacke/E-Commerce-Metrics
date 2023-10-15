package com.commerce.service;

import com.commerce.model.Complaint;
import com.commerce.repository.ComplaintRepository;

import java.util.List;

public class ComplaintService implements Service<Complaint> {

    private ComplaintRepository complaintRepository;

    public ComplaintService(){
        this.complaintRepository = new ComplaintRepository();
    }
    @Override
    public Complaint save(Complaint entity) {
        return complaintRepository.save(entity);
    }

    @Override
    public Complaint findById(Long id) {
        return complaintRepository.findById(id);
    }

    @Override
    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    public Complaint update(Complaint entity){
        return complaintRepository.update(entity);
    }

    @Override
    public boolean delete(Complaint entity) {
        return complaintRepository.delete(entity);
    }
}
