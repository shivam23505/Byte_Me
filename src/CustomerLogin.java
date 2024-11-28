import com.sun.source.tree.Tree;

import javax.security.auth.login.AppConfigurationEntry;
import java.io.*;
import java.util.*;

public class CustomerLogin {

    public Customer checkCustomerLogin(String email, String password, List<Customer> AllCustomer) throws InvalidLoginException {
        for (Customer s : AllCustomer) {
            if (s.getEmail().equals(email) && s.getPassword().equals(password)) {
                return s;
            }
        }
        throw new InvalidLoginException("Invalid login!!");
    }

    public Customer CustomerSignup(Scanner scanner, List<Customer> AllCustomers) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your contact: ");
        String phone = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        // Check if email already exists
        for (Customer s : AllCustomers) {
            if (s.getEmail().equals(email)) {
                System.out.println("Invalid Details. Email already exists!!");
                return null;
            }
        }

        // Create the new customer
        int customerID = AllCustomers.size();
        Customer customer = new Customer(name, address, phone, customerID, email, password, false);
        AllCustomers.add(customer);

        // Save the customer details to customers.txt
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("customers.txt", true))) {
            bw.write(email + "," + password + "," + name + "," + phone + "," + address + "," + (AllCustomers.size() - 1) + ",false");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to customers.txt: " + e.getMessage());
        }

        // Create a specific file for the new customer
        String customerFileName = email + ".txt";
        File customerFile = new File("src", customerFileName);
        try {
            customerFile.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
        return customer;
    }


    public void loginCustomer(Scanner scanner, List<Customer> AllCustomer, TreeSet<FoodItem> Menu,
                              List<Order> AllOrders, PriorityQueue<Order> PendingOrder, List<Order> CancelledOrders) {
        boolean running = true;
        while (running) {
            System.out.println("-------------------------------------------------");
            System.out.println("1.Customer Signup");
            System.out.println("2.Customer login");
            System.out.println("3.Go Back");
            System.out.println("-------------------------------------------------");
            System.out.print("Enter the choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                Customer new_admin = CustomerSignup(scanner, AllCustomer);
                if (new_admin == null) {
                    continue;
                }
                System.out.println("Customer " + new_admin.getEmail() + " successfully signed in!!");
                CustomerMenu(scanner, new_admin, Menu, AllOrders, PendingOrder, AllCustomer, CancelledOrders);
                running = false;
            } else if (choice == 3) {
                running = false;
            } else if (choice == 2) {
                System.out.print("Enter your email:");
                String email = scanner.nextLine();
                System.out.print("Enter your password:");
                String password = scanner.nextLine();
                try {
                    Customer curr_customer = checkCustomerLogin(email, password, AllCustomer);
                    curr_customer.login();
                    CustomerMenu(scanner, curr_customer, Menu, AllOrders, PendingOrder, AllCustomer, CancelledOrders);
                    running = false;
                } catch (InvalidLoginException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void CustomerMenu(Scanner scanner, Customer customer, TreeSet<FoodItem> Menu, List<Order> AllOrders,
                             PriorityQueue<Order> PendingOrder, List<Customer> AllCustomers, List<Order> CancelledOrders) {
        boolean running = true;
        while (running) {
            System.out.println("----------------------");
            System.out.println("1.BROWSE MENU");
            System.out.println("2.ADD A NEW ORDER");
            System.out.println("3.MANAGE MY ORDERS");
            System.out.println("4.OBTAIN MEMBERSHIP");
            System.out.println("5.GIVE REVIEW");
            System.out.println("6.EXIT");
            System.out.print("Enter your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                customer.BrowseMenu(scanner, Menu);
            } else if (choice == 2) {
                customer.PLACE_ORDER(scanner, Menu, AllOrders, AllCustomers, PendingOrder);
            } else if (choice == 3) {
                customer.OrderTracking(scanner, CancelledOrders, PendingOrder);
            } else if (choice == 4) {
                customer.ObtainMembership(scanner, PendingOrder);
            } else if (choice == 5) {
                customer.giveComment(scanner, Menu);
            } else if (choice == 6) {
                customer.saveMyOrders();
                running = false;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }
}
//    public void loadMyOrders(Customer customer) {
//        String fileName = customer.getEmail() + ".txt"; // File name based on the customer email
//        File file = new File(fileName);
//
//        // Check if the file exists
//        if (!file.exists()) {
////            System.out.println("No orders found for this customer.");
//            return;
//        }
//        // Clear the MyOrders list before loading
////        MyOrders.clear();
////        Order MyOrders = new Order();
//        /*
//            private int OrderId;
//            private Map<FoodItem,Integer>order;
//            private int CustomerID;
//            private String CustomerType; // VIP or REGULAR
//            private String SpecialRequirements;
//            private String SpecialRequirements_status;
//            private String Status;
//            private boolean isCancelled;
//
//            private int id;
//    private String name;
//    private String category;
//    private double price;
//    private int quantity;
//    private List<String> Reviews;
//    public Order(int OrderId,Map<FoodItem,Integer> order, int CustomerID, String CustomerType,
//                 String SpecialRequirements, String Status,String SpecialRequirements_status)
//         */
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            String line;
//            // Read the file line by line
//            while ((line = br.readLine()) != null) {
//                String[] data = line.split(",");
//                // Extract order details
//                int orderId = Integer.parseInt(data[0]); // First value is OrderId
//                Map<FoodItem,Integer> foodItems = new HashMap<>();
//                String status = data[data.length - 1]; // Last value is order status
//
//                // Parse the food items in the order
//                for (int i = 1; i < data.length - 1; i += 2) { // Skip every 5 columns (fId, fName, fCategory, fPrice, fQuantity)
//                    int foodId = Integer.parseInt(data[i]);
//                    int foodQuantity = Integer.parseInt(data[i + 4]);
//
//                    FoodItem foodItem = searchItem(foodId);
//                    if (foodItem == null) {
//                        continue;
//                    }
//                    foodItems.put(foodItem, foodQuantity);
//                }
//                // Create an Order object and add it to the list
//                Order order = new Order(orderId, foodItems, customer.getCustomerID(),(customer.getVIP()?"VIP":"REGULAR"),null,status,null);
//                Main.AllOrders.add(order);
//                Main.pendingOrders.add(order);
//                customer.addOrder(order);
//            }
//            System.out.println("Orders loaded successfully.");
//        } catch (IOException e) {
//            System.out.println("Error reading the file: " + e.getMessage());
//        } catch (NumberFormatException e) {
//            System.out.println("Error parsing number: " + e.getMessage());
//        }
//    }
//
//    public FoodItem searchItem(int foodId) {
//        for (FoodItem item:Main.Menu){
//            if (item.getId() == foodId){return item;}
//        }
//        return null;
//    }


