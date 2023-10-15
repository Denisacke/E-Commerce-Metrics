package com.commerce.repository;

import com.commerce.model.Customer;
import com.commerce.model.Order;
import com.commerce.model.Product;
import com.commerce.service.HibernateService;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class CustomerRepository implements Repository<Customer> {

    @Override
    public Customer save(Customer entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        databaseSession.saveOrUpdate(entity);
        databaseSession.save(entity);
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public Customer findById(Long id) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Customer X WHERE X.id = :id");
        query.setParameter("id", id);
        List<Customer> result = query.list();
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    public Customer findByUsername(String username){
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Customer X WHERE X.username = :username");
        query.setParameter("username", username);
        List<Customer> result = query.list();
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    @Override
    public List<Customer> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Customer");
        List<Customer> result = query.list();
        return result;
    }

    @Override
    public boolean delete(Customer entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        if(findById(entity.getId()) != null) {
            Query query = databaseSession.createQuery("DELETE FROM Customer X WHERE X.id = :id");
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
