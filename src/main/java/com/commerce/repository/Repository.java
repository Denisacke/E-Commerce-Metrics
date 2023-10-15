package com.commerce.repository;
import com.commerce.model.AbstractEntity;

import java.util.List;

public interface Repository<T extends AbstractEntity> {

    T save(T entity);

    T findById(Long id);

    List<T> findAll();

    boolean delete(T entity);
}

