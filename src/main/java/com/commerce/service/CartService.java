package com.commerce.service;

import com.commerce.model.Cart;
import com.commerce.repository.CartRepository;

import java.util.List;

public class CartService implements Service<Cart> {

    private CartRepository cartRepository;

    public CartService(){
        this.cartRepository = new CartRepository();
    }

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

    public List<Cart> findByCustomerId(int id_customer){
        return cartRepository.findByCustomerId(id_customer);
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
