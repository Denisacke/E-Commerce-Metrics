package com.commerce.controller;

import com.commerce.constant.Constants;
import com.commerce.model.Employee;
import com.commerce.dto.EmployeeDTO;
import com.commerce.mapper.EmployeeMapper;
import com.commerce.service.EmployeeService;
import com.commerce.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.commerce.constant.Constants.*;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final MailSender mailSender;
    private static final String EMPLOYEES_PAGE = "employees";
    private static final String ADD_EMPLOYEE_PAGE = "add_employee";

    @Autowired
    public EmployeeController(EmployeeService employeeService, MailSender mailSender) {
        this.employeeService = employeeService;
        this.mailSender = mailSender;
    }

    private String handleRestrictedPage(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, ERROR_ACCESS_MESSAGE);

        return Constants.REDIRECT_LINK + Constants.BACKOFFICE_HOME_PAGE;
    }
    @GetMapping("/backoffice/employee")
    public String getEmployees(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        if(request.isUserInRole(Constants.ADMIN_ROLE)) {
            model.addAttribute(EMPLOYEES_PAGE, employeeService.findAll());
            return EMPLOYEES_PAGE;
        }

        return handleRestrictedPage(redirectAttributes);
    }

    @GetMapping("/backoffice/employee/add")
    public String getEmployeeEntryForm(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes){
        if(request.isUserInRole(Constants.ADMIN_ROLE)) {
            EmployeeDTO employeeEntry = new EmployeeDTO();
            model.addAttribute("entry", employeeEntry);
            return ADD_EMPLOYEE_PAGE;
        }

        return handleRestrictedPage(redirectAttributes);
    }

    @PostMapping("/backoffice/employee/add/submit")
    public String addEmployeeEntry(@ModelAttribute @Valid EmployeeDTO form, BindingResult bindingResult, @RequestParam(value = "isAdmin") String check, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Error at input check");
            return Constants.REDIRECT_LINK + Constants.EMPLOYEES_LIST_PAGE;
        }
        form.setAdmin(check.contains("true"));

        if(employeeService.save(EmployeeMapper.mapFromEmployeeDTOToEmployee(form)) != null){
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Account with user: " + form.getUsername() + " has been added");
            mailSender.sendCredentials(form.getEmail(), form.getUsername(), form.getPassword());
        }else{
            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "Error when checking input");
        }

        return Constants.REDIRECT_LINK + EMPLOYEES_LIST_PAGE;
    }

    @GetMapping("/backoffice/employee/delete/{id}")
    public String delEmployeeEntry(Model model, @PathVariable String id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(request.isUserInRole(Constants.ADMIN_ROLE)) {
            Employee entity = employeeService.findById((long) Integer.parseInt(id));
            if (employeeService.delete(entity)) {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "You have successfully deleted: " + entity.getUsername());
            } else {
                redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, "The product you are trying to delete does not exist!");
            }
            return Constants.REDIRECT_LINK + EMPLOYEES_LIST_PAGE;
        }

        return handleRestrictedPage(redirectAttributes);
    }
}
