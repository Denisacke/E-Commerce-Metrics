package com.commerce.service;

import com.commerce.model.Customer;
import com.commerce.repository.CustomerRepository;

import java.util.List;

public class CustomerService implements Service<Customer> {

    private CustomerRepository customerRepository;

    public CustomerService(){
        this.customerRepository = new CustomerRepository();
    }
    @Override
    public Customer save(Customer entity) {
        return customerRepository.save(entity);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer findByUsername(String username){
        return customerRepository.findByUsername(username);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public boolean delete(Customer entity) {
        return customerRepository.delete(entity);
    }
}
