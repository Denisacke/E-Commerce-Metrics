package com.commerce.service;

import com.commerce.model.Order;
import com.commerce.repository.OrderRepository;

import java.util.List;

public class OrderService implements Service<Order> {

    private OrderRepository orderRepository;

    public OrderService(){
        this.orderRepository = new OrderRepository();
    }
    @Override
    public Order save(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public boolean delete(Order entity) {
        return orderRepository.delete(entity);
    }
}
