package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.model.Complaint;
import com.commerce.model.Customer;
import com.commerce.dto.ComplaintDTO;
import com.commerce.mapper.ComplaintMapper;
import com.commerce.service.ComplaintService;
import com.commerce.service.CustomerService;
import com.commerce.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.commerce.constant.Constants.SHOP_PAGE;
import static com.commerce.constant.Constants.SUCCESS_MESSAGE;

@Controller
public class ComplaintController {

    private final ComplaintService complaintService;
    private final CustomerService customerService;
    private final MailSender mailSender;

    @Autowired
    public ComplaintController(ComplaintService complaintService, CustomerService customerService, MailSender mailSender) {
        this.complaintService = complaintService;
        this.customerService = customerService;
        this.mailSender = mailSender;
    }

    @GetMapping("/backoffice/complaint")
    public String getComplaints(Model model){
        model.addAttribute("complaints", complaintService.findAll());
        return "complaints";
    }

    @GetMapping("/backoffice/complaint/view/{id}")
    public String viewComplaint(Model model, @PathVariable String id){
        Complaint entry = complaintService.findById((long) Integer.parseInt(id));

        Customer customer = customerService.findById(entry.getCustomer().getId());
        model.addAttribute("entry", entry);
        model.addAttribute("customer", customer);
        return "view_complaint";
    }

    @GetMapping("/frontoffice/complaint")
    public String renderComplaintForm(Model model){
        model.addAttribute("complaint", new ComplaintDTO());

        return "add_complaint";
    }

    @PostMapping("/frontoffice/complaint/submit")
    public String sendComplaint(@ModelAttribute @Valid ComplaintDTO complaintDTO,
                                RedirectAttributes redirectAttributes,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Error. We could not submit your complaint. Make sure you have filled everything");
            return Constants.REDIRECT_LINK + SHOP_PAGE;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        complaintDTO.setStatus("Pending");
        complaintDTO.setCustomerName(username);

        Complaint complaint = ComplaintMapper
                .mapComplaintDTOToComplaint(
                        complaintDTO,
                        customerService.findByUsername(username)
                );
        complaintService.save(complaint);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "You have successfully submitted a complaint");

        return Constants.REDIRECT_LINK + SHOP_PAGE;
    }

    @PostMapping("/backoffice/complaint/view/{id}/submit")
    public String respondToComplaint(Model model, @PathVariable String id, @RequestParam(value = "action") String actionButton, @RequestParam(value = "email") String email, @RequestParam(value = "response") String response){
        Complaint complaint = complaintService.findById((long) Integer.parseInt(id));
        if(actionButton.equalsIgnoreCase("response")){
            complaint.setStatus("Responded");
            mailSender.sendResponse(email, response);
        }else{
            complaint.setStatus("Spam");
        }
        complaintService.update(complaint);
        return "redirect:/backoffice/complaint";
    }

}
