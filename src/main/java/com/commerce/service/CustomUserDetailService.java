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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("loading.." + s);

        Customer customer = customerRepository.findByUsername(s);
        if(customer != null){
            System.out.println("Got to a customer");
            return new FrontofficeUser(customer);
        }

        Employee employee = employeeRepository.findByUsername(s);
        if(employee == null) {
            return null;
        }
        UserDetails account;
        if (employee.getIsAdmin()) {
            System.out.println("got to admin");
            account = new BackofficeAdmin(employee);
            return account;
        }
        System.out.println("got to simple user");
        account = new BackofficeUser(employee);
        return account;
    }
}
