package com.commerce.service;

import com.commerce.model.Employee;
import com.commerce.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService implements Service<Employee> {

    private EmployeeRepository employeeRepository;

    public EmployeeService(){
        this.employeeRepository = new EmployeeRepository();
    }

    @Override
    public Employee save(Employee entity) {
        return employeeRepository.save(entity);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public boolean delete(Employee entity) {
        return employeeRepository.delete(entity);
    }
}
