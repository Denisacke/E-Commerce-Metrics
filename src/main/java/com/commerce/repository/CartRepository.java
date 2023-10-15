package com.commerce.repository;

import com.commerce.model.Cart;
import com.commerce.model.Category;
import com.commerce.service.HibernateService;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class CartRepository implements Repository<Cart> {
    @Override
    public Cart save(Cart entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        databaseSession.saveOrUpdate(entity);
        databaseSession.save(entity);
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public Cart findById(Long id) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Cart X WHERE X.id = :id");
        query.setParameter("id", id);
        List<Cart> result = query.list();
        return result.get(0);
    }

    public List<Cart> findByCustomerId(int id_customer){
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Cart X WHERE X.id_customer = :id_customer");
        query.setParameter("id_customer", id_customer);
        List<Cart> result = query.list();
        return result;
    }

    @Override
    public List<Cart> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Cart");
        List<Cart> result = query.list();
        return result;
    }

    @Override
    public boolean delete(Cart entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        if(findById(entity.getId()) != null) {
            Query query = databaseSession.createQuery("DELETE FROM Cart X WHERE X.id = :id");
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
