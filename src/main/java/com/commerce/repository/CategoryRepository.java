package com.commerce.repository;

import com.commerce.model.Category;
import com.commerce.service.HibernateService;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

@org.springframework.stereotype.Repository
public class CategoryRepository implements Repository<Category> {

    @Override
    public Category save(Category entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        databaseSession.saveOrUpdate(entity);
        databaseSession.save(entity);
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public Category findById(Long id) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Category X WHERE X.id = :id");
        query.setParameter("id", id);
        return (Category) query.list().get(0);
    }

    public Category findByName(String categoryName) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Category X WHERE X.name = :name");
        query.setParameter("name", categoryName);
        return (Category) query.list().get(0);
    }

    @Override
    public List<Category> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Category");
        return (List<Category>) query.list();
    }

    @Override
    public boolean delete(Category entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        if(findById(entity.getId()) != null) {
            Query query = databaseSession.createQuery("DELETE FROM Category X WHERE X.id = :id");
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
