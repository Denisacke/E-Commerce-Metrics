package com.commerce.repository;

import com.commerce.model.Complaint;
import com.commerce.model.Customer;
import com.commerce.service.HibernateService;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class ComplaintRepository implements Repository<Complaint> {

    @Override
    public Complaint save(Complaint entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        databaseSession.saveOrUpdate(entity);
        databaseSession.save(entity);
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public Complaint findById(Long id) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Complaint X WHERE X.id = :id");
        query.setParameter("id", id);
        List<Complaint> result = query.list();
        return result.get(0);
    }

    public Complaint update(Complaint entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        Query query = databaseSession.createQuery("UPDATE Complaint X SET X.description = :description," +
                                                            "X.id_customer = :id_customer, X.status = :status," +
                                                            "X.type = :type WHERE X.id = :id");
        query.setParameter("description", entity.getDescription());
        query.setParameter("id_customer", entity.getId_customer());
        query.setParameter("status", entity.getStatus());
        query.setParameter("type", entity.getType());
        query.setParameter("id", entity.getId());
        query.executeUpdate();
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public List<Complaint> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Complaint");
        List<Complaint> result = query.list();
        return result;
    }

    @Override
    public boolean delete(Complaint entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        if(findById(entity.getId()) != null) {
            Query query = databaseSession.createQuery("DELETE FROM Complaint X WHERE X.id = :id");
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
