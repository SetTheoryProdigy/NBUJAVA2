# NBUJAVA2

JAVA Documentation Transport Company - Виктория Омарева(F97201) и Димо
Христов(F96503)
Transport Company
Backend:
1. Entities
a. TransportCompany - fields: long id, String name, double income, String date;
i. Comparators by Name and Income
ii. method sortTransportCompanyByNameAndByIncome(
List<TransportCompany> transportCompanies) - sort transport
companies by name and then by income
iii. method String toString() - overrides Object.toString()
b. Driver - fields: long id, String name, String qualification, double salary, long
numDlv, TransportCompany transportCompanyId
i. Comparators by Qualification and Salary
ii. method sortDriversByQualificationAndBySalary(List<Driver> drivers) -
sort transport companies byQualification and then by Salary
iii. method String toString() - overrides Object.toString()
c. Customer - fields: long id, String name, int unpaidFlag,
Set<TransportCompany> transportCompanyId
i. method String toString() - overrides Object.toString()
d. Vehicle - fields:long id, String brand, String vehicleType, Driver driveId
i. method String toString() - overrides Object.toString()
e. Delivery - fields: long id, double dlvWeight, double dlvPrice, String destination,
int startDate, int endDate, String typeDlv, int unpaidFlag, Driver driverId,
Customer customerId, TransportCompany transportCompany
i. Comparators by Destination
ii. method sortDeliveriesByDeliveryDestination(List<Delivery> drivers) to
sort by destination
iii. method String toString() - overrides Object.toString()
2. DAO
a. TransportCompanyDAO
i. saveTransportCompany(TransportCompany transportCompany) -
saves given transport company
ii. readTransportCompanies() - returns all transport companies
iii. getTransportCompany(long id) - returns transport company by ID
iv. deleteTransportCompany(TransportCompany transportCompany) -
deletes transport company by input transport company
v. saveOrUpdateTransportCompany(TransportCompany
transportCompany) - updates transport company by input transport
company
b. DriverDAO
i. static void saveDriver(Driver driver) - saves input driver
ii. static List<Driver> readDrivers() - reads all drivers
iii. static List<Driver> readDriversByCompanyId(long id) - returns all
drivers filtered by transport company ID
iv. static Driver getDriver(long id) - returns driver by driver ID
v. static void deleteDriver(Driver driver) - deletes a driver by input driver
vi. static void deleteDrivers(List<Driver> drivers) - deletes all drivers from
DB provided in input List of Drivers
vii. static void saveOrUpdateDriver(Driver driver) - updates the input
driver
c. CustomerDAO
i. static void saveCustomer(Customer customer) - saves input customer
to DB
ii. static List<Customer> readCustomer() - returns all customers
iii. static Customer getCustomer(long id) - returns customer by Customer
ID
iv. static void deleteCustomer(Customer customer) - deletes a customer
by input customer object
v. static void saveOrUpdateCustomer(Customer customer) - updates
customer by input customer object
vi. static List<Customer> readCustomersByTransportCompanyId(long id)
- returns all customers for given transport company ID
vii. static void deleteCustomers(List<Customer> customers) - deletes all
customer objects from DB provided in input List of Customer objects
d. VehicleDAO
i. static void saveVehicle(Vehicle vehicle) - saves input Vehicle object to
DB
ii. static List<Vehicle> readVehicles() - returns all vehicles from DB to
List of Vehicles
iii. static Vehicle getVehicle(long id) - return Vehicle object from DB by
Vehicle ID
iv. static void deleteVehicle(Vehicle vehicle) - deletes a Vehicle object
from DB
v. static void saveOrUpdateVehicle(Vehicle vehicle) - updates input
Vehicle object in DB
vi. static List<Vehicle> readVehiclesByDriverIds(List<Driver> drivers) -
returns all Vehicle object connected to given List of Driver objects
vii. static void deleteVehicles(List<Vehicle> vehicles) - deletes Vehicle
object from DB based on input List of Vehicle objects
e. DeliveryDAO
i. static void saveDelivery(Delivery delivery) - saves Delivery object in
DB
ii. static List<Delivery> readDeliveries() - returns all Delivery objects from
DB in List of Delivery objects
iii. static Delivery getDelivery(long id) - return Delivery object based on
Delivery ID
iv. static void deleteDelivery(Delivery delivery) - deletes Delivery object
from DB based on input Delivery object
v. static void saveOrUpdateDelivery(Delivery delivery) - updates Delivery
object based on input Delivery object
vi. static List<Delivery>
readDeliveriesByDriverIdsAndCustomerIds(List<Driver> drivers,
List<Customer> customers) - returns List of Delivery objects based on
List of Driver and Customer objects
vii. static List<Delivery> readDeliveriesByDriverId(long id) - returns List of
Delivery objects based on Driver ID
viii. static List<Delivery> readDeliveriesByCustomerId(long id) - returns
List of Delivery objects based on Customer ID
ix. static List<Delivery> readDeliveriesByCustomerIdAndUnpaidFlag(long
id) - returns List of Delivery objects based on Customer ID and Unpaid
Flag (0 - no)
x. static List<Delivery> readDeliveriesByStartDateAndEndDate(int
startDate, int endDate) - turns List of Delivery Objects based on time
period: [startDate - endDate]
xi. static List<Delivery> readDeliveriesByTransportCompanyId(long id) -
returns List of Delivery objects based on Transport Company ID
xii. static void deleteDeliveries(List<Delivery> deliveries) - deletes a List
of Delivery objects that are input to the method
Interface:
1. TransportCompanyInterface
a. Following options:
i. --- 1 --- Choose a Transport company
1. --- 1 --- Driver Menu
a. Call DriverInterface object
2. --- 2 --- Customer Menu
a. Call Customer Interface object
3. --- 3 --- Deliveries Menu
a. Call DeliveryInterfaceObject
4. --- 4 --- Get Income for period of time
5. --- 5 --- Get Company Drivers
6. --- 6 --- Back
ii. --- 2 --- Create a Transport company
iii. --- 3 --- Update a Transport company
iv. --- 4 --- Delete a Transport company
v. --- 5 --- Read Transport Companies
vi. --- 6--- Exit application
2. DeliveryInterface
a. --- 1 --- Create a Delivery
b. --- 2 --- Write Company Deliveries in a file
c. --- 3 --- Read Company Deliveries from a file
d. --- 4 --- Read Company Deliveries
e. --- 5 --- Back
3. CustomerInterface
a. --- 1 --- Choose a Customer
i. -- 1 --- Check Payment Obligations
ii. --- 2 --- Assign Customer to another Transport Company
iii. --- 3 --- Pay Unpaid Delivery
iv. --- 5 --- Back
b. --- 2 --- Create a Customer
c. --- 3 --- Delete a Customer
d. --- 4 --- Back
4. VehicleInterface
a. --- 1 --- Create a Vehicle
b. --- 2 --- Update a Vehicle
c. --- 3 --- Delete a Vehicle
d. --- 4 --- Back
5. DriverInterface
a. --- 1 --- Choose a Driver
i. --- 1 --- Get number of deliveries
ii. --- 2 --- Get driver revenue
iii. --- 3 --- Vehicle Menu
1. Call VehicleInterface
iv. --- 5 --- Back
b. --- 2 --- Create a Driver
c. --- 3 --- Update a Driver
d. --- 4 --- Delete a Driver
e. --- 5 --- Back
