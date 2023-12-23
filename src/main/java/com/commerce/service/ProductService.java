package com.commerce.service;

import com.commerce.model.Product;
import com.commerce.repository.ProductRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class ProductService implements Service<Product> {

    private final ProductRepository productRepository;

    public ProductService(){
        this.productRepository = new ProductRepository();
    }

    public Product save(Product entity) {
        return productRepository.save(entity);
    }

    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByCategory(int categoryId){
        return productRepository.findByCategory(categoryId);
    }

    public Product update(Product entity){
        return productRepository.update(entity);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findAll(String sortCriteria) {
        return productRepository.findSortedProducts(sortCriteria);
    }

    public boolean delete(Product entity) {
        return productRepository.delete(entity);
    }
}
