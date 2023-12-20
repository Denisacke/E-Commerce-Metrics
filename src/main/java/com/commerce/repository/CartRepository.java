package com.commerce.repository;

import com.commerce.model.Cart;
import com.commerce.service.HibernateService;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

@org.springframework.stereotype.Repository
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
        return (Cart) query.list().get(0);
    }

    public List<Cart> findByCustomerId(int id_customer){
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Cart X WHERE X.customerId = :id_customer");
        query.setParameter("id_customer", id_customer);
        return (List<Cart>) query.list();
    }

    @Override
    public List<Cart> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Cart");
        return (List<Cart>) query.list();
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
