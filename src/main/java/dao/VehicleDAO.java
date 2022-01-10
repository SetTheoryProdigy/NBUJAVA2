package dao;

import configuration.SessionFactoryUtil;
import entity.Driver;
import entity.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public static void saveVehicle(Vehicle vehicle) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(vehicle);
            transaction.commit();
        }
    }

    public static List<Vehicle> readVehicles() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM Vehicle a", Vehicle.class).getResultList();
        }
    }

    public static Vehicle getVehicle(long id) {
        Vehicle getVehicle;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            getVehicle = session.get(Vehicle.class, id);
            transaction.commit();
        }
        if (getVehicle == null) {
            System.out.println("No such ID!");
        }
        return getVehicle;
    }

    public static void deleteVehicle(Vehicle vehicle) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(vehicle);
            transaction.commit();
        }
    }

    public static void saveOrUpdateVehicle(Vehicle vehicle) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(vehicle);
            transaction.commit();
        }
    }

    public static List<Vehicle> readVehiclesByDriverIds(List<Driver> drivers) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Vehicle> criteriaQuery = criteriaBuilder.createQuery(Vehicle.class);
            Root<Vehicle> root = criteriaQuery.from(Vehicle.class);

            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(criteriaBuilder.equal(root.get("driver"), drivers));
            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

            Query query = session.createQuery(criteriaQuery);
            List<Vehicle> vehicles = query.getResultList();

            return vehicles;
        }
    }

    public static void deleteVehicles(List<Vehicle> vehicles) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            vehicles.stream().forEach(session::delete);
            transaction.commit();
        }
    }
}
