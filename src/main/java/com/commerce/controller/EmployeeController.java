package com.commerce.controller;

import com.commerce.model.Employee;
import com.commerce.service.EmployeeService;
import com.commerce.service.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class EmployeeController {

    private EmployeeService employeeService = new EmployeeService();

    @GetMapping("/backoffice/employee")
    public String getEmployees(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        if(request.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("employees", employeeService.findAll());
            return "employees";
        }else{
            redirectAttributes.addFlashAttribute("success", "Error. You don't have the permission to access this!");
            return "redirect:/backoffice/home";
        }
    }

    @GetMapping("/backoffice/employee/add")
    public String getEntryForm(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        if(request.isUserInRole("ROLE_ADMIN")) {
            Employee entry = new Employee();
            model.addAttribute("entry", entry);
            return "add_employee";
        }else{
            redirectAttributes.addFlashAttribute("success", "Error. You don't have the permission to access this!");
            return "redirect:/backoffice/home";
        }
    }

    @PostMapping("/backoffice/employee/add/submit")
    public String addEntry(@ModelAttribute @Valid Employee form, BindingResult bindingResult, @RequestParam(value = "isAdmin") String check, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("success", "Error at input check");
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for(int i = 0; i < bindingResult.getErrorCount(); i++){
                System.out.println(errorList.get(i).toString());
            }
            return "redirect:/backoffice/employee";
        }
        form.setAdmin(check.contains("true"));

        if(employeeService.save(form) != null){
            redirectAttributes.addFlashAttribute("success", "Account with user: " + form.getUsername() + " has been added");
        }else{
            redirectAttributes.addFlashAttribute("success", "Error when checking input");
        }
        MailSender.sendCredentials(form.getEmail(), form.getUsername(), form.getPassword());
        return "redirect:/backoffice/employee";
    }

    @GetMapping("/backoffice/employee/delete/{id}")
    public String delEntry(Model model, @PathVariable String id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole("ROLE_ADMIN")) {
            Employee entity = employeeService.findById((long) Integer.parseInt(id));
            if (employeeService.delete(entity)) {
                redirectAttributes.addFlashAttribute("success", "Ai sters cu succes produsul: " + entity.getUsername());
            } else {
                redirectAttributes.addFlashAttribute("success", "Atentie! Produsul pe care incerci sa il stergi nu exista");
            }
            return "redirect:/backoffice/employee";
        }else{
            redirectAttributes.addFlashAttribute("success", "Error. You don't have the permission to access this!");
            return "redirect:/backoffice/home";
        }
    }
}
