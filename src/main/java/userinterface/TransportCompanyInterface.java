package userinterface;

import dao.*;
import entity.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TransportCompanyInterface {

    public TransportCompanyInterface() {
        Scanner sc = new Scanner(System.in);
        int choice = 1;
        int oldChoice = 1;
        while (choice >= 1 && choice <= 6) {
            System.out.println("Transport Company");
            System.out.println("Please choose one of the options:");
            System.out.println(" --- 1 --- Choose a Transport company");
            System.out.println(" --- 2 --- Create a Transport company");
            System.out.println(" --- 3 --- Update a Transport company");
            System.out.println(" --- 4 --- Delete a Transport company");
            System.out.println(" --- 5 --- Read Transport Companies");
            System.out.println(" --- 6 --- Exit application");
            choice = sc.nextInt();
            int id;
            switch (choice) {
                case 1:
                    oldChoice = choice;
                    TransportCompany useTransportCompany = null;
                    while (useTransportCompany == null) {
                        System.out.println("Enter transport company ID for further options:");
                        id = sc.nextInt();
                        useTransportCompany = TransportCompanyDAO.getTransportCompany(id);
                        if (useTransportCompany != null) {
                            break;
                        } else {
                            System.out.println("Invalid Transport Company ID! Please try again!");
                        }
                    }
                    choice = 1;
                    loop:
                    while (choice >= 1 && choice <= 6) {
                        System.out.println("Choose one of the following for further process for" +
                                "Transport Company " + useTransportCompany.getName());
                        System.out.println(" --- 1 --- Driver Menu");
                        System.out.println(" --- 2 --- Customer Menu");
                        System.out.println(" --- 3 --- Deliveries Menu");
                        System.out.println(" --- 4 --- Get Income for period of time:");
                        System.out.println(" --- 5 --- Get Company Drivers");
                        System.out.println(" --- 6 --- Back");
                        choice = sc.nextInt();
                        switch (choice) {
                            case 1: // Driver Menu
                                DriverInterface driverInterface = new DriverInterface(useTransportCompany);
                                break;
                            case 2: // Customer Menu
                                CustomerInterface customerInterface = new CustomerInterface(useTransportCompany);
                                break;
                            case 3: // Deliveries Menu
                                DeliveryInterface deliveryInterface = new DeliveryInterface(useTransportCompany);
                                break;
                            case 4: // Get Income for period of time:
                                System.out.println("Enter start delivery date (YYYYMMDD format):");
                                int startDate = sc.nextInt();

                                System.out.println("Enter end delivery date (YYYYMMDD format):");
                                int endDate = sc.nextInt();

                                double revenue = 0;
                                List<Delivery> deliveries = DeliveryDAO.readDeliveriesByStartDateAndEndDate(startDate, endDate);
                                for (Delivery delivery : deliveries) {
                                    revenue += delivery.getDlvPrice();
                                }
                                System.out.println("For period: " + startDate + "-" + endDate + " revenue of the company is " +
                                        revenue);
                                break;
                            case 5: // Get Company Drivers
                                List<Driver> drivers = DriverDAO.readDriversByCompanyId(useTransportCompany.getId());
                                drivers.sort(Driver.DriverQualification.thenComparing(Driver.DriverSalary));
                                drivers.stream().forEach(System.out::println);
                                break;
                            case 6:
                                break loop;
                        }
                    }
                    choice = oldChoice;
                    break;
                case 2:
                    oldChoice = choice;
                    System.out.println("Enter transport company name:");
                    String name = sc.next();

                    TransportCompany newTransportCompany =
                            new TransportCompany(1, name, 0, LocalDate.now().toString());
                    TransportCompanyDAO.saveTransportCompany(newTransportCompany);
                    System.out.println("Successful creation!");
                    choice = oldChoice;
                    break;
                case 3:
                    oldChoice = choice;
                    System.out.println("Enter transport company ID for update:");
                    id = sc.nextInt();
                    TransportCompany updateTransportCompany = TransportCompanyDAO.getTransportCompany(id);

                    System.out.println("Choose field for update of given object");
                    System.out.println(" --- 1 --- Name");
                    System.out.println(" --- 5 --- Back");

                    choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("Enter new transport company name:");
                            String newName = sc.nextLine();
                            updateTransportCompany.setName(newName);
                            TransportCompanyDAO.saveOrUpdateTransportCompany(updateTransportCompany);
                            System.out.println("Update successful!");
                    }
                    choice = oldChoice;
                    break;
                case 4:
                    oldChoice = choice;
                    System.out.println("Enter transport company ID for deletion:");
                    id = sc.nextInt();
                    TransportCompany deleteTransportCompany = TransportCompanyDAO.getTransportCompany(id);

                    List<Driver> deleteDrivers = DriverDAO.readDriversByCompanyId(id);
                    List<Vehicle> deleteVehicles = VehicleDAO.readVehiclesByDriverIds(deleteDrivers);
                    List<Customer> deleteCustomers = CustomerDAO.readCustomersByTransportCompanyId(id);
                    List<Delivery> deleteDeliveries = DeliveryDAO.readDeliveriesByDriverIdsAndCustomerIds(deleteDrivers, deleteCustomers);

                    // Delete all connect to chosen transport company ID
                    DeliveryDAO.deleteDeliveries(deleteDeliveries);
                    CustomerDAO.deleteCustomers(deleteCustomers);
                    VehicleDAO.deleteVehicles(deleteVehicles);
                    DriverDAO.deleteDrivers(deleteDrivers);
                    TransportCompanyDAO.deleteTransportCompany(deleteTransportCompany);
                    System.out.println("Deletion successful");
                    choice = oldChoice;
                    break;
                case 5: // Read All Transport Companies
                    oldChoice = choice;
                    List<TransportCompany> allTransportCompanies = TransportCompanyDAO.readTransportCompanies();
                    if (allTransportCompanies.isEmpty()){
                        System.out.println("No Transport Company objects found in DB");
                    }
                    else{
                        TransportCompany.sortTransportCompanyByNameAndByIncome(allTransportCompanies);
                        allTransportCompanies.stream().forEach(System.out::println);
                    }
                    choice = oldChoice;
                    break;
                case 6:
                    sc.close();
                    System.exit(0);
                    break;
            }
        }

    }
}
