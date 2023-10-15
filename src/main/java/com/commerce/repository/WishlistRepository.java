package com.commerce.repository;

import com.commerce.model.Wishlist;
import com.commerce.model.Wishlist;
import com.commerce.service.HibernateService;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class WishlistRepository implements Repository<Wishlist> {

    @Override
    public Wishlist save(Wishlist entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        databaseSession.saveOrUpdate(entity);
        databaseSession.save(entity);
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public Wishlist findById(Long id) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Wishlist X WHERE X.id = :id");
        query.setParameter("id", id);
        List<Wishlist> result = query.list();
        return result.get(0);
    }

    public List<Wishlist> findByCustomerId(int id_customer){
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Wishlist X WHERE X.id_customer = :id_customer");
        query.setParameter("id_customer", id_customer);
        List<Wishlist> result = query.list();
        return result;
    }

    @Override
    public List<Wishlist> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Wishlist");
        List<Wishlist> result = query.list();
        return result;
    }

    @Override
    public boolean delete(Wishlist entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        if (findById(entity.getId()) != null) {
            Query query = databaseSession.createQuery("DELETE FROM Wishlist X WHERE X.id = :id");
            query.setParameter("id", entity.getId());
            query.executeUpdate();
            databaseSession.flush();
            databaseSession.getTransaction().commit();
            return true;
        } else {
            return false;
        }
    }
}
