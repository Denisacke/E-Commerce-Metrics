package com.commerce.repository;

import com.commerce.model.Order;
import com.commerce.model.Product;
import com.commerce.model.Stock;
import com.commerce.service.HibernateService;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class StockRepository implements Repository<Stock> {

    @Override
    public Stock save(Stock entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        Query query = databaseSession.createQuery("FROM Stock X WHERE X.id = :id");
        query.setParameter("id", entity.getId());
        List<Stock> result = query.list();
        if(result.size() == 0) {
            databaseSession.saveOrUpdate(entity);
            databaseSession.save(entity);
            databaseSession.flush();
            databaseSession.getTransaction().commit();
        }
        else {
            return null;
        }
        return entity;
    }

    @Override
    public Stock findById(Long id) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Stock X WHERE X.id = :id");
        query.setParameter("id", id);
        List<Stock> result = query.list();
        return result.get(0);
    }

    @Override
    public List<Stock> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Stock");
        List<Stock> result = query.list();
        return result;
    }

    @Override
    public boolean delete(Stock entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        if(findById(entity.getId()) != null) {
            Query query = databaseSession.createQuery("DELETE FROM Stock X WHERE X.id = :id");
            query.setParameter("id", entity.getId());
            query.executeUpdate();
            databaseSession.flush();
            databaseSession.getTransaction().commit();
            return true;
        }
        else {
            return false;
        }
    }
}
