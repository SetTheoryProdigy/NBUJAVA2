package dao;

import configuration.SessionFactoryUtil;
import entity.Customer;
import entity.Delivery;
import entity.Driver;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {
    public static void saveDelivery(Delivery delivery) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(delivery);
            transaction.commit();
        }
    }

    public static List<Delivery> readDeliveries() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM Delivery a", Delivery.class).getResultList();
        }
    }

    public static Delivery getDelivery(long id) {
        Delivery getDelivery;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            getDelivery = session.get(Delivery.class, id);
            transaction.commit();
        }
        if (getDelivery == null) {
            System.out.println("No such ID!");
        }
        return getDelivery;
    }

    public static void deleteDelivery(Delivery delivery) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(delivery);
            transaction.commit();
        }
    }

    public static void saveOrUpdateDelivery(Delivery delivery) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(delivery);
            transaction.commit();
        }
    }

    public static List<Delivery> readDeliveriesByDriverIdsAndCustomerIds(List<Driver> drivers, List<Customer> customers) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Delivery> criteriaQuery = criteriaBuilder.createQuery(Delivery.class);
            Root<Delivery> root = criteriaQuery.from(Delivery.class);

            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(criteriaBuilder.equal(root.get("driver"), drivers));
            predicates.add(criteriaBuilder.equal(root.get("customer"), customers));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

            Query query = session.createQuery(criteriaQuery);
            List<Delivery> deliveries = query.getResultList();

            return deliveries;
        }
    }

    public static List<Delivery> readDeliveriesByDriverId(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Delivery> criteriaQuery = criteriaBuilder.createQuery(Delivery.class);
            Root<Delivery> root = criteriaQuery.from(Delivery.class);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("driver"), id));

            Query query = session.createQuery(criteriaQuery);
            List<Delivery> deliveries = query.getResultList();

            return deliveries;
        }
    }

    public static List<Delivery> readDeliveriesByCustomerId(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Delivery> criteriaQuery = criteriaBuilder.createQuery(Delivery.class);
            Root<Delivery> root = criteriaQuery.from(Delivery.class);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("customer"), id));

            Query query = session.createQuery(criteriaQuery);
            List<Delivery> deliveries = query.getResultList();

            return deliveries;
        }
    }

    public static List<Delivery> readDeliveriesByCustomerIdAndUnpaidFlag(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Delivery> criteriaQuery = criteriaBuilder.createQuery(Delivery.class);
            Root<Delivery> root = criteriaQuery.from(Delivery.class);

            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(criteriaBuilder.equal(root.get("customer"), id));
            predicates.add(criteriaBuilder.equal(root.get("unpaidFlag"), 1));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

            Query query = session.createQuery(criteriaQuery);
            List<Delivery> deliveries = query.getResultList();

            return deliveries;
        }
    }

    public static List<Delivery> readDeliveriesByStartDateAndEndDate(int startDate, int endDate) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Delivery> criteriaQuery = criteriaBuilder.createQuery(Delivery.class);
            Root<Delivery> root = criteriaQuery.from(Delivery.class);

            criteriaQuery.select(root).where(criteriaBuilder.ge(root.get("startDate"), startDate));
            criteriaQuery.select(root).where(criteriaBuilder.le(root.get("endDate"), endDate));

            Query query = session.createQuery(criteriaQuery);
            List<Delivery> deliveries = query.getResultList();

            return deliveries;
        }
    }

    public static List<Delivery> readDeliveriesByTransportCompanyId(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Delivery> criteriaQuery = criteriaBuilder.createQuery(Delivery.class);
            Root<Delivery> root = criteriaQuery.from(Delivery.class);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("transportCompany"), id));

            Query query = session.createQuery(criteriaQuery);
            List<Delivery> deliveries = query.getResultList();

            return deliveries;
        }
    }


    public static void deleteDeliveries(List<Delivery> deliveries) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            deliveries.stream().forEach(session::delete);
            transaction.commit();
        }
    }
}
