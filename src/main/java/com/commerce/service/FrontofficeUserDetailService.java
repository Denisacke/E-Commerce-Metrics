package com.commerce.service;

import com.commerce.model.Customer;
import com.commerce.model.Employee;
import com.commerce.repository.CustomerRepository;
import com.commerce.repository.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FrontofficeUserDetailService implements UserDetailsService {

    private CustomerRepository customerRepository = new CustomerRepository();

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(s);
        UserDetails account = new FrontofficeUser(customer);
        return account;
    }

}
