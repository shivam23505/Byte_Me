import com.sun.source.tree.Tree;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

public class CustomerLogin {
    public Customer checkCustomerLogin(String email,String password,List<Customer>AllCustomer) throws InvalidLoginException{
        for (Customer s:AllCustomer){
            if (s.getEmail().equals(email) && s.getPassword().equals(password)){
                return s;
            }
        }
        throw new InvalidLoginException("Invalid login!!");
    }

    public Customer CustomerSignup(Scanner scanner,List<Customer>AllCustomers){
        System.out.print("Enter your name:");
        String name = scanner.nextLine();
        System.out.print("Enter your email:");
        String email = scanner.nextLine();
        System.out.print("Enter your password:");
        String password = scanner.nextLine();
        System.out.print("Enter your contact:");
        String phone = scanner.nextLine();
        System.out.print("Enter your address:");
        String address = scanner.nextLine();
        for(Customer s:AllCustomers){
            if (s.getEmail().equals(email)){
                System.out.println("Invalid Details. Email already exists!!");
                return null;
            }
        }
        int customerID = AllCustomers.size();
        Customer customer = new Customer(name,address,phone,customerID,email,password,false);
        AllCustomers.add(customer);
        return customer;
    }

    public void loginCustomer(Scanner scanner, List<Customer>AllCustomer, TreeSet<FoodItem> Menu,
                              List<Order>AllOrders, PriorityQueue<Order>PendingOrder,List<Order>CancelledOrders) {
        boolean running = true;
        while(running) {
            System.out.println("-------------------------------------------------");
            System.out.println("1.Customer Signup");
            System.out.println("2.Customer login");
            System.out.println("3.Go Back");
            System.out.println("-------------------------------------------------");
            System.out.print("Enter the choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                Customer new_admin = CustomerSignup(scanner,AllCustomer);
                if(new_admin == null){continue;}
                System.out.println("Customer " + new_admin.getEmail() + " successfully signed in!!");
                CustomerMenu(scanner,new_admin,Menu,AllOrders,PendingOrder,AllCustomer,CancelledOrders);
                running=false;
            } else if (choice == 3) {
                running = false;
            } else if (choice == 2) {
                System.out.print("Enter your email:");
                String email = scanner.nextLine();
                System.out.print("Enter your password:");
                String password = scanner.nextLine();
                try{
                    Customer curr_customer = checkCustomerLogin(email,password,AllCustomer);
                    curr_customer.login();
                    CustomerMenu(scanner,curr_customer,Menu,AllOrders,PendingOrder,AllCustomer,CancelledOrders);
                    running=false;
                }
                catch(InvalidLoginException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    public void CustomerMenu(Scanner scanner, Customer customer, TreeSet<FoodItem> Menu,List<Order>AllOrders,
                             PriorityQueue<Order>PendingOrder,List<Customer>AllCustomers, List<Order>CancelledOrders) {
        boolean running = true;
        while(running) {
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
                customer.BrowseMenu(scanner,Menu);
            }
            else if (choice == 2) {
                customer.PLACE_ORDER(scanner,Menu,AllOrders,AllCustomers,PendingOrder);
            }
            else if (choice == 3) {
                customer.OrderTracking(scanner,CancelledOrders,PendingOrder);
            }
            else if (choice == 4) {
                customer.ObtainMembership(scanner, PendingOrder);
            }
            else if (choice == 5) {
                customer.giveComment(scanner,Menu);
            }
            else if (choice == 6) {
                running = false;
            }
            else {
                System.out.println("Invalid choice!");
            }
        }
    }
}
