package entity;

import javax.persistence.*;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "vehicleType", nullable = false)
    private String vehicleType;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    public Vehicle() {

    }

    public Vehicle(String brand) {
        this.brand = brand;
    }

    public Vehicle(long id, String brand, String vehicleType, Driver driveId) {
        this.id = id;
        this.brand = brand;
        this.vehicleType = vehicleType;
        this.driver = driveId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "idVehicle= " + getId() +
                ", brand= " + getBrand() +
                ", driverId= " + getDriver().getId() + "}";
    }
}
