package com.commerce.service;

import com.commerce.model.Wishlist;
import com.commerce.model.Wishlist;
import com.commerce.repository.WishlistRepository;
import com.commerce.repository.WishlistRepository;

import java.util.List;

public class WishlistService implements Service<Wishlist>{

    private WishlistRepository wishlistRepository;

    public WishlistService(){
        this.wishlistRepository = new WishlistRepository();
    }

    public WishlistService(WishlistRepository wishlistRepository){
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public Wishlist save(Wishlist entity) {
        return wishlistRepository.save(entity);
    }

    @Override
    public Wishlist findById(Long id) {
        return wishlistRepository.findById(id);
    }

    public List<Wishlist> findByCustomerId(int id_customer){
        return wishlistRepository.findByCustomerId(id_customer);
    }

    @Override
    public List<Wishlist> findAll() {
        return wishlistRepository.findAll();
    }

    @Override
    public boolean delete(Wishlist entity) {
        return wishlistRepository.delete(entity);
    }
}
