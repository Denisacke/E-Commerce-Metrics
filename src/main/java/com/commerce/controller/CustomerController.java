package com.commerce.controller;

import com.commerce.model.Cart;
import com.commerce.model.Customer;
import com.commerce.service.CustomerService;
import com.commerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class CustomerController {

    private CustomerService customerService = new CustomerService();

    @GetMapping("/backoffice/customer")
    public String getCustomers(Model model){
        model.addAttribute("customers", customerService.findAll());

        return "customers";
    }

    @GetMapping("/frontoffice/signup")
    public String getEntryFrom(Model model){
        Customer entry = new Customer();
        model.addAttribute("entry", entry);
        return "signup_page";
    }

    @PostMapping("/frontoffice/signup/submit")
    public String addEntry(@ModelAttribute Customer form, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if(customerService.save(form) != null){
            redirectAttributes.addFlashAttribute("success", "Account with user: " + form.getUsername() + " has been added");
        }else{
            redirectAttributes.addFlashAttribute("success", "Error when checking input");
        }
        return "redirect:/frontoffice";
    }

//    @GetMapping("/backoffice/customer/add")
//    public String getEntryForm(Model model){
//        Customer entry = new Customer();
//        model.addAttribute("entry", entry);
//        return "add_customer";
//    }
//
//    @PostMapping("/backoffice/customer/add/submit")
//    public String addEntry(@ModelAttribute Customer form, Model model, RedirectAttributes redirectAttributes) {
//        if(customerService.save(form) != null){
//            redirectAttributes.addFlashAttribute("success", "Account with user: " + form.getUsername() + " has been added");
//        }else{
//            redirectAttributes.addFlashAttribute("success", "Eroare la verificarea datelor!");
//        }
//
//        return "redirect:/backoffice/customer";
//    }
}
