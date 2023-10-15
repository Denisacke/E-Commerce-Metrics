package com.commerce.repository;

import com.commerce.model.Order;
import com.commerce.model.Product;
import com.commerce.service.HibernateService;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class OrderRepository implements Repository<Order> {

    @Override
    public Order save(Order entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        databaseSession.saveOrUpdate(entity);
        databaseSession.save(entity);
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public Order findById(Long id) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Order X WHERE X.id = :id");
        query.setParameter("id", id);
        List<Order> result = query.list();
        return result.get(0);
    }

    @Override
    public List<Order> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Order");
        List<Order> result = query.list();
        return result;
    }

    @Override
    public boolean delete(Order entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        if(findById(entity.getId()) != null) {
            Query query = databaseSession.createQuery("DELETE FROM Order X WHERE X.id = :id");
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
