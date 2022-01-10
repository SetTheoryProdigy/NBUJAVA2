package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "unpaid_flag", nullable = false)
    private int unpaidFlag;

    @ManyToMany
    private Set<TransportCompany> transportCompany = new HashSet<TransportCompany>();

    @OneToMany(mappedBy = "customer")
    private List<Delivery> deliveries;

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public Customer(long id, String name, int unpaidFlag, Set<TransportCompany> transportCompanyId) {
        this.id = id;
        this.name = name;
        this.unpaidFlag = unpaidFlag;
        this.transportCompany = transportCompanyId;
    }

    public Customer(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.unpaidFlag = customer.getUnpaidFlag();
        this.transportCompany = customer.getTransportCompany();
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

    public int getUnpaidFlag() {
        return unpaidFlag;
    }

    public void setUnpaidFlag(int unpaidFlag) {
        this.unpaidFlag = unpaidFlag;
    }

    public Set<TransportCompany> getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(Set<TransportCompany> transportCompany) {
        this.transportCompany = transportCompany;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", paidFlag = " + unpaidFlag +
                '}';
    }
}
