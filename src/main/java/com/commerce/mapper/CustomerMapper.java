package com.commerce.mapper;

import com.commerce.model.Customer;
import com.commerce.dto.CustomerDTO;

public class CustomerMapper {

    private CustomerMapper(){

    }

    public static Customer mapFromCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();

        customer.setUsername(customerDTO.getUsername());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(customerDTO.getPassword());

        return customer;
    }
}
