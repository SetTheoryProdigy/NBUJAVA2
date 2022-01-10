package userinterface;

import dao.*;
import entity.*;

import java.util.*;

public class CustomerInterface {
    private TransportCompany transportCompany;

    public CustomerInterface(TransportCompany transportCompany) {
        Scanner sc = new Scanner(System.in);
        int choice = 1;
        int oldChoice = 1;
        int id;
        loopOut:
        while (choice >= 1 && choice <= 4) {
            System.out.println("Customers Menu for company " + transportCompany.getName());
            System.out.println("Please choose one of the options:");
            System.out.println(" --- 1 --- Choose a Customer");
            System.out.println(" --- 2 --- Create a Customer");
            System.out.println(" --- 3 --- Delete a Customer");
            System.out.println(" --- 4 --- Back");
            choice = sc.nextInt();
            List<TransportCompany> useTransportCompany = TransportCompanyDAO.readTransportCompanies(); // read all companies for further use
            switch (choice) {
                case 1: // Choose a Customer
                    oldChoice = choice;
                    Customer useCustomer = null;
                    while (useCustomer == null) {
                        System.out.println("Enter customer ID for further options:");
                        id = sc.nextInt();
                        useCustomer = CustomerDAO.getCustomer(id);
                        if (useCustomer != null) {
                            break;
                        } else {
                            System.out.println("Invalid Customer ID! Please try again!");
                        }
                    }
                    choice = 1;
                    loop:
                    while ((choice >= 1 && choice <= 2) || choice == 5) {
                        System.out.println("Customer " + useCustomer.getName());
                        System.out.println("Please choose one of the options:");
                        System.out.println(" --- 1 --- Check Payment Obligations");
                        System.out.println(" --- 2 --- Assign Customer to another Transport Company");
                        System.out.println(" --- 3 --- Pay Unpaid Delivery");
                        System.out.println(" --- 5 --- Back");
                        choice = sc.nextInt();
                        switch (choice) {
                            case 1: // Check Payment Obligations
                                System.out.println("Payment Obligations for customer " + useCustomer.getId() +
                                        ":" + (useCustomer.getUnpaidFlag() == 0 ? "no obligations" : "have obligations"));
                                List<Delivery> unpaidDeliveries = DeliveryDAO.readDeliveriesByCustomerIdAndUnpaidFlag(useCustomer.getId());
                                unpaidDeliveries.stream().forEach(System.out::println);
                                break;
                            case 2: // Assign Customer to one more Transport Company
                                TransportCompany assignCustomerCompany = null;
                                while (assignCustomerCompany == null) {
                                    useTransportCompany.stream().forEach(System.out::println);
                                    System.out.println("Enter Transport Company to assign customer " + useCustomer.getName() +
                                            " ID " + useCustomer.getId());
                                    int trCompId = sc.nextInt();
                                    assignCustomerCompany = TransportCompanyDAO.getTransportCompany(trCompId);
                                    if (assignCustomerCompany == null) {
                                        System.out.println("Invalid Transport Company ID, please choose again!");
                                    } else {
                                        Customer assignCompanyCustomer = new Customer(useCustomer);
                                        Set<TransportCompany> assignTransportCompany = new HashSet<TransportCompany>();
                                        assignTransportCompany.add(assignCustomerCompany);
                                        assignCompanyCustomer.setTransportCompany(assignTransportCompany);
                                        CustomerDAO.saveOrUpdateCustomer(assignCompanyCustomer);
                                        break;
                                    }
                                }
                                break;
                            case 3: // Pay Unpaid Delivery
                                Delivery deliveryToPay = null;
                                while (deliveryToPay == null) {
                                    List<Delivery> unpaidDeliveriesShow = DeliveryDAO.readDeliveriesByCustomerIdAndUnpaidFlag(useCustomer.getId());
                                    if (unpaidDeliveriesShow == null) {
                                        Customer paidCustomer = new Customer(useCustomer);
                                        paidCustomer.setUnpaidFlag(0);
                                        CustomerDAO.saveOrUpdateCustomer(paidCustomer);
                                        System.out.println("All Payment Obligation Paid!");
                                        break;
                                    }
                                    unpaidDeliveriesShow.stream().forEach(System.out::println);
                                    System.out.println("Enter Delivery (ID) to pay");
                                    int deliveryId = sc.nextInt();
                                    deliveryToPay = DeliveryDAO.getDelivery(deliveryId);
                                    if (deliveryToPay != null) {
                                        deliveryToPay.setUnpaidFlag(0);
                                        DeliveryDAO.saveOrUpdateDelivery(deliveryToPay);


                                        Driver paidDriver = DriverDAO.getDriver(deliveryToPay.getDriver().getId());
                                        paidDriver.setNumDlv(paidDriver.getNumDlv() + 1);
                                        DriverDAO.saveOrUpdateDriver(paidDriver);
                                    }
                                }
                                break;
                            case 5:
                                break loop;
                        }
                    }
                    choice = oldChoice;
                    break;
                case 2: // Create a Customer
                    oldChoice = choice;

                    System.out.println("Enter customer name:");
                    String name = sc.next();

                    Set<TransportCompany> newTransportCompanyCustomer = new HashSet<TransportCompany>();
                    long companyId = 0;
                    while (newTransportCompanyCustomer.isEmpty()) {
                        while (companyId >= 0) {
                            useTransportCompany.stream().forEach(System.out::println);
                            System.out.println("Enter company ID (Enter negative number to stop the entering of companies)");
                            int compId = sc.nextInt();

                            if (compId < 0){
                                break;
                            }

                            if (useTransportCompany.stream().filter(a -> a.getId() == compId).findAny() == null) {
                                System.out.println("Invalid ID, please choose again!");
                            } else {
                                newTransportCompanyCustomer.add(useTransportCompany.stream().filter(a -> a.getId() == compId).findFirst().get());
                            }
                        }
                    }

                    Customer newCustomer = new Customer(1l, name, 0, newTransportCompanyCustomer);
                    CustomerDAO.saveCustomer(newCustomer);
                    System.out.println("Successful Customer creation!");
                    choice = oldChoice;
                    break;
                case 3: // Delete
                    oldChoice = choice;
                    System.out.println("Enter Customer ID for deletion:");
                    id = sc.nextInt();
                    Customer deleteCustomer = CustomerDAO.getCustomer(id);

                    List<Customer> customers = new ArrayList<Customer>();
                    customers.add(deleteCustomer);

                    List<Delivery> deleteDeliveries = DeliveryDAO.readDeliveriesByCustomerId(id);
                    DeliveryDAO.deleteDeliveries(deleteDeliveries);

                    CustomerDAO.deleteCustomer(deleteCustomer);

                    System.out.println("Deletion Successful");
                    choice = oldChoice;
                    break;
                case 4:
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
}
