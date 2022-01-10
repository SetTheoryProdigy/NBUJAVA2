package userinterface;

import dao.DeliveryDAO;
import dao.DriverDAO;
import dao.VehicleDAO;
import entity.Delivery;
import entity.Driver;
import entity.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class VehicleInterface {
    private Driver driver;

    public VehicleInterface(Driver driver) {
        setDriver(driver);
        Scanner sc = new Scanner(System.in);
        int choice = 1;
        int oldChoice = 1;
        int id;
        loopOut:
        while (choice >= 1 && choice <= 4) {
            System.out.println("Vehicles Menu for company " + driver.getTransportCompany().getId());
            System.out.println("Please choose one of the options:");
            System.out.println(" --- 1 --- Create a Vehicle");
            System.out.println(" --- 2 --- Update a Vehicle");
            System.out.println(" --- 3 --- Delete a Vehicle");
            System.out.println(" --- 4 --- Back");
            choice = sc.nextInt();
            switch (choice) {
                case 1: // Create a Vehicle
                    oldChoice = choice;
                    System.out.println("Enter Vehicle Brand");
                    String brand = sc.next();

                    System.out.println("Enter Vehicle Type(BUS,CAR,CISTERN,TRUCK)");
                    String typeVehicle = sc.next().toUpperCase(Locale.ROOT);

                    Vehicle newVehicle = new Vehicle(1, brand, typeVehicle, driver);
                    VehicleDAO.saveVehicle(newVehicle);
                    choice = oldChoice;
                    break;
                case 2: // Update a Vehicle
                    oldChoice = choice;

                    System.out.println("Enter vehicle ID for update:");
                    id = sc.nextInt();
                    Vehicle updateVehicle = VehicleDAO.getVehicle(id);

                    choice = 1;
                    loop:
                    while ((choice >= 1 && choice <= 2) || choice == 5) {
                        System.out.println("Choose field for update of vehicle " + updateVehicle.getId());
                        System.out.println(" --- 1 --- Brand");
                        System.out.println(" --- 2 --- Type");
                        System.out.println(" --- 5 --- Back");

                        choice = sc.nextInt();
                        switch (choice) {
                            case 1:
                                System.out.println("Enter new Brand name:");
                                String newBrand = sc.nextLine();
                                updateVehicle.setBrand(newBrand);
                                VehicleDAO.saveVehicle(updateVehicle);
                                System.out.println("Update successful for brand!");
                            case 2:
                                System.out.println("Enter new Vehicle Type (BUS,CAR,CISTERN,TRUCK):");
                                String vehicleType = sc.nextLine();
                                updateVehicle.setVehicleType(vehicleType);
                                VehicleDAO.saveVehicle(updateVehicle);
                                System.out.println("Update successful for vehicle type!");
                            case 5:
                                break loop;
                        }
                    }
                    choice = oldChoice;
                    break;
                case 3: // Delete a Vehicle
                    oldChoice = choice;
                    System.out.println("Enter vehicle ID for deletion:");
                    id = sc.nextInt();
                    Vehicle deleteVehicle = VehicleDAO.getVehicle(id);

                    VehicleDAO.deleteVehicle(deleteVehicle);
                    System.out.println("Deletion Successful");
                    choice = oldChoice;
                    break;
                case 4: // Back
                    break loopOut;
            }

        }
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }


}
