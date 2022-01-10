package userinterface;

import dao.CustomerDAO;
import dao.DeliveryDAO;
import dao.DriverDAO;
import entity.Customer;
import entity.Delivery;
import entity.Driver;
import entity.TransportCompany;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class DeliveryInterface {
    public TransportCompany transportCompany;
    public String fileDeliveriesName;

    public DeliveryInterface(TransportCompany transportCompany) {
        setTransportCompany(transportCompany);
        Scanner sc = new Scanner(System.in);
        this.fileDeliveriesName = transportCompany.getId() + ".bin";
        File file = new File(fileDeliveriesName);

        int choice = 1;
        int oldChoice = 1;
        int id;
        loopOut:
        while (choice >= 1 && choice <= 5) {
            System.out.println("Delivery Menu for company " + transportCompany.getName());
            System.out.println("Please choose one of the options:");
            System.out.println(" --- 1 --- Create a Delivery");
            System.out.println(" --- 2 --- Write Company Deliveries in a file");
            System.out.println(" --- 3 --- Read Company Deliveries from a file");
            System.out.println(" --- 4 --- Read Company Deliveries");
            System.out.println(" --- 5 --- Back");
            choice = sc.nextInt();
            switch (choice) {
                case 1: // Create a Delivery
                    oldChoice = choice;
                    List<Driver> drivers = DriverDAO.readDriversByCompanyId(transportCompany.getId());
                    drivers.stream().forEach(System.out::println);
                    System.out.println("Choose a driver for delivery: ");
                    id = sc.nextInt();
                    int finalIdDriver = id;
                    Driver driverDelivery = drivers.stream().filter(a -> a.getId() == finalIdDriver).findFirst().get();

                    List<Customer> customers = CustomerDAO.readCustomersByTransportCompanyId(transportCompany.getId());
                    customers.stream().forEach(System.out::println);
                    System.out.println("Choose a customer for delivery: ");
                    id = sc.nextInt();
                    int finalIdCustomer = id;
                    Customer customerDelivery = customers.stream().filter(a -> a.getId() == finalIdCustomer).findFirst().get();

                    System.out.println("Enter delivery weight:");
                    double dlvWeight = sc.nextDouble();

                    System.out.println("Enter delivery price:");
                    double dlvPrice = sc.nextDouble();

                    System.out.println("Enter delivery destination:");
                    String destination = sc.next();

                    System.out.println("Enter start delivery date (YYYYMMDD format):");
                    int startDate = sc.nextInt();

                    System.out.println("Enter end delivery date (YYYYMMDD format):");
                    int endDate = sc.nextInt();

                    System.out.println("Enter delivery type (OUTBOUND,INBOUND):");
                    String typeDlv = sc.next().toUpperCase(Locale.ROOT);

                    int unpaidFlag = 1;

                    Delivery newDelivery = new Delivery(1, dlvWeight, dlvPrice, destination, startDate, endDate, typeDlv, unpaidFlag,
                            driverDelivery, customerDelivery, transportCompany);
                    DeliveryDAO.saveDelivery(newDelivery);

                    choice = oldChoice;
                    break;
                case 2: // Write Company Deliveries in a file
                    oldChoice = choice;
                    List<Delivery> deliveries = DeliveryDAO.readDeliveriesByTransportCompanyId(transportCompany.getId());

                    writeDeliveriesFile(deliveries);

                    choice = oldChoice;
                    break;
                case 3:
                    oldChoice = choice;
                    List<Delivery> deliveriesFile = readDeliveriesFile();
                    deliveriesFile.stream().forEach(System.out::println);
                    choice = oldChoice;
                    break;
                case 4:
                    oldChoice = choice;
                    List<Delivery> deliveriesAll = DeliveryDAO.readDeliveriesByTransportCompanyId(transportCompany.getId());
                    deliveriesAll.sort(Delivery.DeliveryDestination);
                    deliveriesAll.stream().forEach(System.out::println);
                    choice = oldChoice;
                    break;
                case 5: // Back
                    break loopOut;
            }
        }
    }

    public TransportCompany getTransportCompany() {
        return transportCompany;
    }

    public void setTransportCompany(TransportCompany transportCompany) {
        this.transportCompany = transportCompany;
    }

    public String getFileDeliveriesName() {
        return fileDeliveriesName;
    }

    public void setFileDeliveriesName(String fileDeliveriesName) {
        this.fileDeliveriesName = fileDeliveriesName;
    }

    public List<Delivery> readDeliveriesFile() {
        List<Delivery> deliveries = new ArrayList<Delivery>();
        try (FileInputStream fi = new FileInputStream(getFileDeliveriesName())) {
            ObjectInputStream os = new ObjectInputStream(fi);
            boolean loop = true;
            try {
                while (loop) {
                    Delivery delivery = (Delivery) os.readObject();
                    deliveries.add(delivery);
                }
            } catch (EOFException e) {
                loop = false;
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (ClassNotFoundException e) {
        }


        return deliveries;
    }

    public void writeDeliveriesFile(List<Delivery> deliveries) {
        try (FileOutputStream fs = new FileOutputStream(getFileDeliveriesName())) {
            ObjectOutputStream os = new ObjectOutputStream(fs);

            deliveries.stream().forEach(a -> {
                try {
                    os.writeObject(a);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("Successful write!");
            os.close();
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
}
