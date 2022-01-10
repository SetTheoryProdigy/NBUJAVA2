package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "transport_company")
public class TransportCompany implements Comparable<TransportCompany>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "income", nullable = false)
    private double income;

    @Column(name = "date", nullable = false)
    private String date;

    @OneToMany(mappedBy = "transportCompany")
    private List<Driver> drivers;

    @OneToMany(mappedBy = "transportCompany")
    private List<Delivery> deliveries;

    public TransportCompany() {
    }

    public TransportCompany(String name) {
        this.name = name;
    }

    public TransportCompany(long id, String name, double income, String date) {
        this.id = id;
        this.name = name;
        this.income = income;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "TransportCompany{" +
                "id=" + id +
                ", date=" + date +
                ", income" + getIncome() +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(TransportCompany o) {
        return this.name.compareTo(o.name);
    }

    public static Comparator<TransportCompany> TransportCompanyName =
            new Comparator<TransportCompany>() {
                @Override
                public int compare(TransportCompany transportCompany1, TransportCompany transportCompany2) {

                    return transportCompany1.name.compareTo(transportCompany2.name);
                }
            };

    public static Comparator<TransportCompany> TransportCompanyIncome =
            new Comparator<TransportCompany>() {
                @Override
                public int compare(TransportCompany transportCompany1, TransportCompany transportCompany2) {
                    return Double.compare(transportCompany1.income, transportCompany2.income);
                }
            };


    public static void sortTransportCompanyByNameAndByIncome(List<TransportCompany> transportCompanies) {
        Collections
                .sort(transportCompanies,
                        TransportCompany.TransportCompanyName
                                .thenComparing(TransportCompany.TransportCompanyIncome));
    }
}
