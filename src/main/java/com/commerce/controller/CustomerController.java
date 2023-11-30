package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.model.Customer;
import com.commerce.model.dto.CustomerDTO;
import com.commerce.model.mapper.CustomerMapper;
import com.commerce.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.commerce.constant.Constants.FRONTOFFICE_HOME_PAGE;
import static com.commerce.constant.Constants.SUCCESS_MESSAGE;

@Controller
public class CustomerController {

    private final CustomerService customerService = new CustomerService();

    @GetMapping("/backoffice/customer")
    public String getCustomers(Model model){
        model.addAttribute("customers", customerService.findAll());

        return "customers";
    }

    @GetMapping("/frontoffice/signup")
    public String getEntryFrom(Model model){
        CustomerDTO entry = new CustomerDTO();
        model.addAttribute("entry", entry);
        return "signup_page";
    }

    @PostMapping("/frontoffice/signup/submit")
    public String addEntry(@ModelAttribute CustomerDTO form, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if(customerService.save(CustomerMapper.mapFromCustomerDTOToCustomer(form)) != null){
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Account with user: " + form.getUsername() + " has been added");
        }else{
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Error when checking input");
        }
        return Constants.REDIRECT_LINK + FRONTOFFICE_HOME_PAGE;
    }
}
