package com.commerce.repository;

import com.commerce.model.Product;
import com.commerce.service.HibernateService;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class ProductRepository implements Repository<Product> {

    public Product save(Product entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        Query query = databaseSession.createQuery("FROM Product X WHERE X.name = :name");
        query.setParameter("name", entity.getName());
        List<Product> result = query.list();
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


    public Product update(Product entity){
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        Query query = databaseSession.createQuery("UPDATE Product X SET X.description = :description," +
                                                            " X.name = :name, X.price = :price, X.id_category = :category," +
                                                            " X.picture = :picture WHERE X.id = :id");
        query.setParameter("name", entity.getName());
        query.setParameter("id", entity.getId());
        query.setParameter("description", entity.getDescription());
        query.setParameter("price", entity.getPrice());
        query.setParameter("picture", entity.getPicture());
        query.setParameter("category", entity.getId_category());
        query.executeUpdate();
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    public Product findById(Long id) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Product X WHERE X.id = :id");
        query.setParameter("id", id);
        List<Product> result = query.list();
        return result.get(0);
    }

    public List<Product> findByCategory(int id_category){
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Product X WHERE X.id_category = :id_category");
        query.setParameter("id_category", id_category);
        List<Product> result = query.list();
        return result;
    }

    public List<Product> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("from Product");
        List<Product> result = query.list();
        return result;
    }


    public boolean delete(Product entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        if(findById(entity.getId()) != null) {
            Query query = databaseSession.createQuery("DELETE FROM Product X WHERE X.id = :id");
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
