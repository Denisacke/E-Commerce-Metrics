package com.commerce.service;

import com.commerce.model.Customer;
import com.commerce.model.Employee;
import com.commerce.repository.CustomerRepository;
import com.commerce.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomUserDetailService(EmployeeRepository employeeRepository,
                                   CustomerRepository customerRepository) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByUsername(s);
        if(customer != null){
            return new FrontofficeUser(customer);
        }

        Employee employee = employeeRepository.findByUsername(s);
        if(employee == null) {
            return null;
        }
        UserDetails account;
        if (Boolean.TRUE.equals(employee.getIsAdmin())) {
            account = new BackofficeAdmin(employee);
            return account;
        }
        account = new BackofficeUser(employee);
        return account;
    }
}
