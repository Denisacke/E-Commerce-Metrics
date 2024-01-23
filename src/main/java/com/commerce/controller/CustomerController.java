package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.dto.CustomerDTO;
import com.commerce.mapper.CustomerMapper;
import com.commerce.service.CustomerService;
import com.commerce.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.commerce.constant.Constants.*;

@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final MailSender mailSender;

    @Autowired
    public CustomerController(CustomerService customerService, MailSender mailSender) {
        this.customerService = customerService;
        this.mailSender = mailSender;
    }

    @GetMapping("/customer/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if(customerService.delete(customerService.findById(id))){
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "The customer has been successfully deleted");
        } else {
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "An error occurred when trying to delete the customer");
        }
        return Constants.REDIRECT_LINK + CUSTOMERS_LIST_PAGE;
    }

    @GetMapping("/backoffice/customer")
    public String getCustomers(Model model){
        model.addAttribute("customers", customerService.findAll());

        return "customers";
    }

    @GetMapping("/backoffice/customer/add")
    public String getCustomerCreationForm(Model model){
        CustomerDTO entry = new CustomerDTO();
        model.addAttribute("entry", entry);
        return "add_customer";
    }

    @GetMapping("/frontoffice/signup")
    public String getEntryForm(Model model){
        CustomerDTO entry = new CustomerDTO();
        model.addAttribute("entry", entry);
        return "signup_page";
    }

    @PostMapping({"/frontoffice/signup/submit", "/backoffice/customer/add/submit"})
    public String addCustomerEntry(@ModelAttribute CustomerDTO form, RedirectAttributes redirectAttributes) {
        if(customerService.save(CustomerMapper.mapFromCustomerDTOToCustomer(form)) != null){
            mailSender.sendCredentials(form.getEmail(), form.getUsername(), form.getPassword());
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Account with user: " + form.getUsername() + " has been added");
        }else{
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Error when checking input");
        }
        return Constants.REDIRECT_LINK + FRONTOFFICE_HOME_PAGE;
    }
}
