package com.commerce.service;

import com.commerce.model.Stock;
import com.commerce.repository.StockRepository;

import java.util.List;

public class StockService implements Service<Stock> {

    private StockRepository stockRepository;

    public StockService(){
        this.stockRepository = new StockRepository();
    }
    @Override
    public Stock save(Stock entity) {
        return stockRepository.save(entity);
    }

    @Override
    public Stock findById(Long id) {
        return stockRepository.findById(id);
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public boolean delete(Stock entity) {
        return stockRepository.delete(entity);
    }
}
