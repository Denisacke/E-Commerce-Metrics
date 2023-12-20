package com.commerce.service;

import com.commerce.model.Category;
import com.commerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class CategoryService implements Service<Category> {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findByName(String categoryName){ return categoryRepository.findByName(categoryName); }
    @Override
    public boolean delete(Category entity) {
        return categoryRepository.delete(entity);
    }
}
