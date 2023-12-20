package com.commerce.service;

import com.commerce.model.Cart;
import com.commerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class CartService implements Service<Cart> {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart save(Cart entity) {
        return cartRepository.save(entity);
    }

    @Override
    public Cart findById(Long id) {
        return cartRepository.findById(id);
    }

    public List<Cart> findByCustomerId(int customerId){
        return cartRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public boolean delete(Cart entity) {
        return cartRepository.delete(entity);
    }
}
