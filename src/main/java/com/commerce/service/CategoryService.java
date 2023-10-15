package com.commerce.service;

import com.commerce.model.Category;
import com.commerce.repository.CategoryRepository;

import java.util.List;

public class CategoryService implements Service<Category> {

    private CategoryRepository categoryRepository;

    public CategoryService(){
        this.categoryRepository = new CategoryRepository();
    }

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

    @Override
    public boolean delete(Category entity) {
        return categoryRepository.delete(entity);
    }
}
