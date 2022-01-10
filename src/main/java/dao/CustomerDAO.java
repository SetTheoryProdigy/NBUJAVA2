package dao;

import configuration.SessionFactoryUtil;
import entity.Customer;
import entity.Driver;
import entity.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomerDAO {
    public static void saveCustomer(Customer customer) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        }
    }

    public static List<Customer> readCustomer() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM Customer a", Customer.class).getResultList();
        }
    }

    public static Customer getCustomer(long id) {
        Customer getCustomer;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            getCustomer = session.get(Customer.class, id);
            transaction.commit();
        }
        if (getCustomer == null) {
            System.out.println("No such ID!");
        }
        return getCustomer;
    }

    public static void deleteCustomer(Customer customer) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(customer);
            transaction.commit();
        }
    }

    public static void saveOrUpdateCustomer(Customer customer) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(customer);
            transaction.commit();
        }
    }

    public static List<Customer> readCustomersByTransportCompanyId(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));

            Query query = session.createQuery(criteriaQuery);
            List<Customer> customers = query.getResultList();

            return customers;
        }
    }

    public static void deleteCustomers(List<Customer> customers) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            customers.stream().forEach(session::delete);
            transaction.commit();
        }
    }
}
