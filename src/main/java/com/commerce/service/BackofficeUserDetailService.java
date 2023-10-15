package com.commerce.service;

import com.commerce.model.Customer;
import com.commerce.model.Employee;
import com.commerce.repository.CustomerRepository;
import com.commerce.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public class BackofficeUserDetailService implements UserDetailsService {

    private EmployeeRepository employeeRepository = new EmployeeRepository();

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(s);
        UserDetails account;
        if(employee.getIsAdmin()){
            System.out.println("got to admin");
            account = new BackofficeAdmin(employee);
            return account;
        }
        System.out.println("got to simple user");
        account = new BackofficeUser(employee);
        return account;
    }

}
