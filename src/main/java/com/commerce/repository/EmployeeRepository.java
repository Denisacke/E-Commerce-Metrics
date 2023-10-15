package com.commerce.repository;

import com.commerce.model.Customer;
import com.commerce.model.Employee;
import com.commerce.service.HibernateService;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class EmployeeRepository implements Repository<Employee>{

    @Override
    public Employee save(Employee entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        Query query = databaseSession.createQuery("FROM Employee X WHERE X.username = :username OR X.email = :email");
        query.setParameter("username", entity.getUsername());
        query.setParameter("email", entity.getEmail());
        List<Employee> result = query.list();
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

    public Employee findByUsername(String username){
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Employee X WHERE X.username = :username");
        query.setParameter("username", username);
        List<Employee> result = query.list();
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    @Override
    public Employee findById(Long id) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Employee X WHERE X.id = :id");
        query.setParameter("id", id);
        List<Employee> result = query.list();
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    @Override
    public List<Employee> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM Employee");
        List<Employee> result = query.list();
        return result;
    }

    @Override
    public boolean delete(Employee entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        if(findById(entity.getId()) != null) {
            Query query = databaseSession.createQuery("DELETE FROM Employee X WHERE X.id = :id");
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
