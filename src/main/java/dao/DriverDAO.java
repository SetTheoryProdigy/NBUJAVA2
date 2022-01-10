package dao;

import configuration.SessionFactoryUtil;
import entity.Driver;
import entity.TransportCompany;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DriverDAO {
    public static void saveDriver(Driver driver) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(driver);
            transaction.commit();
        }
    }

    public static List<Driver> readDrivers() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM Driver a", Driver.class).getResultList();
        }
    }

    public static List<Driver> readDriversByCompanyId(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Driver> criteriaQuery = criteriaBuilder.createQuery(Driver.class);
            Root<Driver> root = criteriaQuery.from(Driver.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));

            Query query = session.createQuery(criteriaQuery);
            List<Driver> drivers = query.getResultList();

            return drivers;
        }
    }

    public static Driver getDriver(long id) {
        Driver getDriver;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            getDriver = session.get(Driver.class, id);
            transaction.commit();
        }
        if (getDriver == null) {
            System.out.println("No such ID!");
        }
        return getDriver;
    }

    public static void deleteDriver(Driver driver) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(driver);
            transaction.commit();
        }
    }

    public static void deleteDrivers(List<Driver> drivers) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            drivers.stream().forEach(session::delete);
            transaction.commit();
        }
    }

    public static void saveOrUpdateDriver(Driver driver) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(driver);
            transaction.commit();
        }
    }
}
