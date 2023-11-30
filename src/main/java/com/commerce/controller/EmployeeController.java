package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.model.Employee;
import com.commerce.model.dto.EmployeeDTO;
import com.commerce.model.mapper.EmployeeMapper;
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

import static com.commerce.constant.Constants.ERROR_ACCESS_MESSAGE;
import static com.commerce.constant.Constants.SUCCESS_MESSAGE;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService = new EmployeeService();

    @GetMapping("/backoffice/employee")
    public String getEmployees(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        if(request.isUserInRole(Constants.ADMIN_ROLE)) {
            model.addAttribute("employees", employeeService.findAll());
            return "employees";
        }else{
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, ERROR_ACCESS_MESSAGE);
            return Constants.REDIRECT_LINK + Constants.BACKOFFICE_HOME_PAGE;
        }
    }

    @GetMapping("/backoffice/employee/add")
    public String getEntryForm(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        if(request.isUserInRole(Constants.ADMIN_ROLE)) {
            EmployeeDTO entry = new EmployeeDTO();
            model.addAttribute("entry", entry);
            return "add_employee";
        }else{
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, ERROR_ACCESS_MESSAGE);
            return Constants.REDIRECT_LINK + Constants.BACKOFFICE_HOME_PAGE;
        }
    }

    @PostMapping("/backoffice/employee/add/submit")
    public String addEntry(@ModelAttribute @Valid EmployeeDTO form, BindingResult bindingResult, @RequestParam(value = "isAdmin") String check, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Error at input check");
            return Constants.REDIRECT_LINK + Constants.EMPLOYEES_LIST_PAGE;
        }
        form.setAdmin(check.contains("true"));

        if(employeeService.save(EmployeeMapper.mapFromEmployeeDTOToEmployee(form)) != null){
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Account with user: " + form.getUsername() + " has been added");
        }else{
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Error when checking input");
        }
        MailSender.sendCredentials(form.getEmail(), form.getUsername(), form.getPassword());
        return "redirect:/backoffice/employee";
    }

    @GetMapping("/backoffice/employee/delete/{id}")
    public String delEntry(Model model, @PathVariable String id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.ADMIN_ROLE)) {
            Employee entity = employeeService.findById((long) Integer.parseInt(id));
            if (employeeService.delete(entity)) {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Ai sters cu succes produsul: " + entity.getUsername());
            } else {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Atentie! Produsul pe care incerci sa il stergi nu exista");
            }
            return "redirect:/backoffice/employee";
        }else{
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, ERROR_ACCESS_MESSAGE);
            return Constants.REDIRECT_LINK + Constants.BACKOFFICE_HOME_PAGE;
        }
    }
}
