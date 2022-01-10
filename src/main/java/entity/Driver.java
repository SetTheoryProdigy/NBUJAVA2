package entity;

import helper.QualificationEnum;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "driver")
public class Driver implements Comparable<Driver>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "qualification", nullable = false)
    private String qualification;

    @Column(name = "salary", nullable = false)
    private double salary;

    @Column(name = "numDlv")
    private long numDlv;

    @ManyToOne
    @JoinColumn(name = "transportCompany_id", nullable = false)
    private TransportCompany transportCompany;

    @OneToMany(mappedBy = "driver")
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "driver")
    private List<Delivery> deliveries;

    public Driver() {
    }

    public Driver(String name) {
        this.name = name;
    }

    public Driver(long id, String name, String qualification, double salary, long numDlv, TransportCompany transportCompanyId) {
        this.id = id;
        this.name = name;
        this.qualification = qualification;
        this.salary = salary;
        this.numDlv = numDlv;
        this.transportCompany = transportCompanyId;
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

    public TransportCompany getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(TransportCompany transportCompany) {
        this.transportCompany = transportCompany;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getNumDlv() {
        return numDlv;
    }

    public void setNumDlv(long numDlv) {
        this.numDlv = numDlv;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "iddriver=" + id +
                ", name='" + name + '\'' +
                ", qualificationEnum=" + getQualification().toString() +
                ", salary" + getSalary() +
                '}';
    }

    @Override
    public int compareTo(Driver o) {
        return this.name.compareTo(o.name);
    }

    public static Comparator<Driver> DriverQualification =
            new Comparator<Driver>() {
                @Override
                public int compare(Driver driver1, Driver driver2) {

                    return driver1.qualification.compareTo(driver1.qualification);
                }
            };

    public static Comparator<Driver> DriverSalary =
            new Comparator<Driver>() {
                @Override
                public int compare(Driver driver1, Driver driver2) {
                    return Double.compare(driver1.salary, driver2.salary);
                }
            };


    public static void sortDriversByQualificationAndBySalary(List<Driver> drivers) {
        Collections
                .sort(drivers,
                        Driver.DriverQualification
                                .thenComparing(Driver.DriverSalary));
    }
}
