package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "delivery")
public class Delivery implements Comparable<Delivery>, Serializable {

    private static final long serialVersionUID = 1234L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "dlvWeight")
    private double dlvWeight;

    @Column(name = "dlvPrice")
    private double dlvPrice;

    @Column(name = "destination")
    private String destination;

    @Column(name = "startDate")
    private int startDate;

    @Column(name = "endDate")
    private int endDate;

    @Column(name = "typeDlv")
    private String typeDlv;

    @Column(name = "unpaidFlag")
    private int unpaidFlag;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "transportCompany_id", nullable = false)
    private TransportCompany transportCompany;

    public Delivery() {

    }

    public Delivery(long id, double dlvWeight, double dlvPrice, String destination,
                    int startDate, int endDate, String typeDlv, int unpaidFlag,
                    Driver driverId, Customer customerId, TransportCompany transportCompany) {
        this.id = id;
        this.dlvWeight = dlvWeight;
        this.dlvPrice = dlvPrice;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.typeDlv = typeDlv;
        this.unpaidFlag = unpaidFlag;
        this.driver = driverId;
        this.customer = customerId;
        this.transportCompany = transportCompany;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getDlvWeight() {
        return dlvWeight;
    }

    public void setDlvWeight(double dlvWeight) {
        this.dlvWeight = dlvWeight;
    }

    public double getDlvPrice() {
        return dlvPrice;
    }

    public void setDlvPrice(double dlvPrice) {
        this.dlvPrice = dlvPrice;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public String getTypeDlv() {
        return typeDlv;
    }

    public void setTypeDlv(String typeDlv) {
        this.typeDlv = typeDlv;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getUnpaidFlag() {
        return unpaidFlag;
    }

    public void setUnpaidFlag(int unpaidFlag) {
        this.unpaidFlag = unpaidFlag;
    }

    public TransportCompany getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(TransportCompany transportCompany) {
        this.transportCompany = transportCompany;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", dlvWeight='" + dlvWeight +
                ", dlvPrice='" + dlvPrice +
                ", destination='" + destination +
                ", startDate='" + startDate +
                ", endDate='" + endDate +
                ", typeDlv='" + typeDlv +
                ", unpaidFlag=" + unpaidFlag +
                ", driverId='" + driver.getId() +
                ", customerId='" + customer.getId() +
                "}";
    }

    @Override
    public int compareTo(Delivery o) {
        return this.destination.compareTo(o.destination);
    }

    public static Comparator<Delivery> DeliveryDestination =
            new Comparator<Delivery>() {
                @Override
                public int compare(Delivery driver1, Delivery driver2) {
                    return Delivery.DeliveryDestination.compare(driver1, driver2);
                }
            };


    public static void sortDeliveriesByDeliveryDestination(List<Delivery> drivers) {
        Collections
                .sort(drivers,
                        Delivery.DeliveryDestination);
    }


}
