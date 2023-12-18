package com.commerce.mapper;

import com.commerce.model.Employee;
import com.commerce.dto.EmployeeDTO;

public class EmployeeMapper {

    private EmployeeMapper(){

    }

    public static Employee mapFromEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setAdmin(employeeDTO.getIsAdmin());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPassword(employeeDTO.getPassword());
        employee.setUsername(employeeDTO.getUsername());

        return employee;
    }
}
