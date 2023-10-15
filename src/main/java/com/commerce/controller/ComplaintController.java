package com.commerce.controller;

import com.commerce.model.Complaint;
import com.commerce.model.Customer;
import com.commerce.model.Product;
import com.commerce.service.ComplaintService;
import com.commerce.service.CustomerService;
import com.commerce.service.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ComplaintController {

    private ComplaintService complaintService = new ComplaintService();

    @GetMapping("/backoffice/complaint")
    public String getComplaints(Model model){
        model.addAttribute("complaints", complaintService.findAll());
        return "complaints";
    }

    @GetMapping("/backoffice/complaint/view/{id}")
    public String viewComplaint(Model model, @PathVariable String id){
        Complaint entry = complaintService.findById((long) Integer.parseInt(id));
        CustomerService customerService = new CustomerService();
        Customer customer = customerService.findById((long) entry.getId_customer());
        model.addAttribute("entry", entry);
        model.addAttribute("customer", customer);
        return "view_complaint";
    }

    @PostMapping("/backoffice/complaint/view/{id}/submit")
    public String respondToComplaint(Model model, @PathVariable String id, @RequestParam(value = "action") String actionButton, @RequestParam(value = "email") String email, @RequestParam(value = "response") String response){
        Complaint complaint = complaintService.findById((long) Integer.parseInt(id));
        if(actionButton.equalsIgnoreCase("response")){
            complaint.setStatus("Responded");
            MailSender.sendResponse(email, response);
        }else{
            complaint.setStatus("Spam");
        }
        complaintService.update(complaint);
        return "redirect:/backoffice/complaint";
    }

}
