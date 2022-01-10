package userinterface;

import dao.DeliveryDAO;
import dao.DriverDAO;
import dao.VehicleDAO;
import entity.Delivery;
import entity.Driver;
import entity.TransportCompany;
import entity.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverInterface {

    private TransportCompany transportCompany;

    public DriverInterface(TransportCompany transportCompany) {
        setTransportCompany(transportCompany);
        Scanner sc = new Scanner(System.in);
        int choice = 1;
        int oldChoice = 1;
        int id;
        while (choice >= 1 && choice <= 5) {
            System.out.println("Drivers Menu for company " + getTransportCompany().getName());
            System.out.println("Please choose one of the options:");
            System.out.println(" --- 1 --- Choose a Driver");
            System.out.println(" --- 2 --- Create a Driver");
            System.out.println(" --- 3 --- Update a Driver");
            System.out.println(" --- 4 --- Delete a Driver");
            System.out.println(" --- 5 --- Back");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    oldChoice = choice;
                    Driver useDriver = null;
                    while (useDriver == null) {
                        System.out.println("Enter driver ID for further options:");
                        id = sc.nextInt();
                        useDriver = DriverDAO.getDriver(id);
                        if (useDriver != null) {
                            break;
                        } else {
                            System.out.println("Invalid Driver ID! Please try again!");
                        }
                    }
                    choice = 1;
                    loop:
                    while ((choice >= 1 && choice <= 3) || choice <= 5) {
                        System.out.println("Driver " + useDriver.getName());
                        System.out.println("Please choose one of the options:");
                        System.out.println(" --- 1 --- Get number of deliveries ");
                        System.out.println(" --- 2 --- Get driver revenue");
                        System.out.println(" --- 3 --- Vehicle Menu");
                        System.out.println(" --- 5 --- Back");
                        choice = sc.nextInt();
                        switch (choice) {
                            case 1:
                                System.out.println("Number of deliveries for driver ID " + useDriver.getId() +
                                        ":" + useDriver.getNumDlv());
                                break;
                            case 2:
                                List<Delivery> useDeliveries = DeliveryDAO.readDeliveriesByDriverId(useDriver.getId());
                                double revenue = 0d;
                                for (Delivery delivery : useDeliveries) {
                                    revenue += delivery.getDlvPrice();
                                }
                                System.out.println("Revenue for driver ID " + useDriver.getId() +
                                        ":" + revenue);
                                break;
                            case 3:
                                VehicleInterface vehicleInterface = new VehicleInterface(useDriver);
                            case 5:
                                break loop;
                        }
                    }
                    choice = oldChoice;
                case 2: // Create
                    oldChoice = choice;
                    System.out.println("Enter driver name:");
                    String name = sc.next();

                    System.out.println("Enter driver qualification (possible values - A,B,C,D):");
                    String qualification = sc.next();

                    System.out.println("Enter driver salary:");
                    double salary = sc.nextDouble();

                    Driver newDriver = new Driver(1, name, qualification, salary, 0l, transportCompany);
                    DriverDAO.saveDriver(newDriver);
                    System.out.println("Successful creation!");
                    choice = oldChoice;
                    break;
                case 3: // Update
                    oldChoice = choice;
                    System.out.println("Enter driver ID for update:");
                    id = sc.nextInt();
                    Driver updateDriver = DriverDAO.getDriver(id);

                    choice = 1;
                    loop:
                    while ((choice >= 1 && choice <= 2) || choice == 5) {
                        System.out.println("Choose field for update of driver " + updateDriver.getName() +
                                "ID: " + updateDriver.getId());
                        System.out.println(" --- 1 --- Qualification");
                        System.out.println(" --- 2 --- Salary");
                        System.out.println(" --- 5 --- Back");

                        choice = sc.nextInt();
                        switch (choice) {
                            case 1:
                                System.out.println("Enter new driver qualification (possible values - A,B,C,D):");
                                String newQualification = sc.nextLine();
                                updateDriver.setQualification(newQualification);
                                DriverDAO.saveOrUpdateDriver(updateDriver);
                                System.out.println("Update successful for qualification!");
                            case 2:
                                System.out.println("Enter new driver salary:");
                                double newSalary = sc.nextDouble();
                                updateDriver.setSalary(newSalary);
                                DriverDAO.saveOrUpdateDriver(updateDriver);
                                System.out.println("Update successful!");
                            case 5:
                                break loop;
                        }
                    }
                    choice = oldChoice;
                    break;
                case 4: // Delete
                    oldChoice = choice;
                    System.out.println("Enter driver ID for deletion:");
                    id = sc.nextInt();
                    Driver deleteDriver = DriverDAO.getDriver(id);

                    List<Driver> drivers = new ArrayList<Driver>();
                    drivers.add(deleteDriver);
                    List<Vehicle> deleteVehicles = VehicleDAO.readVehiclesByDriverIds(drivers);
                    VehicleDAO.deleteVehicles(deleteVehicles);

                    List<Delivery> deleteDeliveries = DeliveryDAO.readDeliveriesByDriverId(id);
                    DeliveryDAO.deleteDeliveries(deleteDeliveries);

                    DriverDAO.deleteDriver(deleteDriver);

                    System.out.println("Deletion Successful");
                    choice = oldChoice;
                case 5: // Back
                    break;
            }
        }
    }


    public TransportCompany getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(TransportCompany transportCompany) {
        this.transportCompany = transportCompany;
    }
}
