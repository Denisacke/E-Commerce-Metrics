package com.commerce.service;

import com.commerce.model.Product;
import com.commerce.repository.ProductRepository;

import java.util.List;

public class ProductService implements Service<Product> {

    private ProductRepository productRepository;

    public ProductService(){
        this.productRepository = new ProductRepository();
    }

    public Product save(Product entity) {
        return productRepository.save(entity);
    }

    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByCategory(int id_category){
        return productRepository.findByCategory(id_category);
    }

    public Product update(Product entity){
        return productRepository.update(entity);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public boolean delete(Product entity) {
        return productRepository.delete(entity);
    }
}
