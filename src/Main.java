import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static TreeSet<FoodItem> Menu = new TreeSet<>(new MenuIDComparator()); // Menu sorted by item ID
    public static PriorityQueue<Order> pendingOrders = new PriorityQueue<>(); // Orders sorted by priority (VIP first)
    public static List<Admin>Admins = new ArrayList<>();
    public static List<Customer>Customers = new ArrayList<>();
    public static List<Order>CancelledOrders = new ArrayList<>();
    public static List<Order> AllOrders = new ArrayList<>();
    public static AdminLogin adminLogin = new AdminLogin();
    public static CustomerLogin customerLogin = new CustomerLogin();

    public static void main(String[] args) throws InvalidLoginException{
        RetrieveMenu();
        RetrieveCustomers();
        viewMenu();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("-------------------------------------------------");
            System.out.println("########### BYTE ME!! Food Delivery Application###########");
            System.out.println("1.Login as Customer");
            System.out.println("2.Login as Admin");
            System.out.println("3.Exit the application");
            System.out.println("-------------------------------------------------");
            System.out.print("Enter the choice:");

            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                customerLogin.loginCustomer(scanner,Customers,Menu,AllOrders,pendingOrders,CancelledOrders);
            } else if (choice == 2) {
                adminLogin.loginAdmin(scanner,Admins,Menu,AllOrders,pendingOrders);
            } else if (choice == 3) {
                System.out.println("Thank you for using the application!!");
                running = false;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }
    public static void RetrieveCustomers() throws InvalidLoginException {
        File file = new File("customers.txt");
        if (!file.exists()) {
            throw new InvalidLoginException("No customer data available!");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(","); // Assuming format: email,password
                if (details.length == 7) {
                    loadCustomer(details);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public static void loadCustomer(String[] details) throws InvalidLoginException {
//        bw.write(email + "," + password + "," + name +","+phone + ","+address+","+(AllCustomers.size() - 1) + ",false");
        String email = details[0];
        String password = details[1];
        String name = details[2];
        String phone = details[3];
        String address = details[4];
        int Id = Integer.parseInt(details[5]);
        boolean type = Boolean.parseBoolean(details[6]);
        for (Customer customer : Customers) {
            if (email.equals(customer.getEmail())) {
                return;
            }
        }
        Customer customer = new Customer(name,address,phone,Id,email,password,type);
        customer.loadMyOrders();
        Customers.add(customer);
    }

    public static void RetrieveMenu() throws InvalidLoginException {
        File file = new File("menu.txt");
        if (!file.exists()) {
            throw new InvalidLoginException("Menu data is not available!");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 5) { // Ensure the line has exactly 5 values
                    System.out.println("Invalid menu data: " + line);
                    continue;
                }
                try {
                    int foodId = Integer.parseInt(data[0]);
                    String foodName = data[1];
                    String foodCategory = data[2];
                    double foodPrice = Double.parseDouble(data[3]);
                    int foodQuantity = Integer.parseInt(data[4]);

                    // Create FoodItem and add to Menu
                    FoodItem foodItem = new FoodItem(foodId, foodName, foodCategory, foodPrice, foodQuantity);
                    Menu.add(foodItem);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing menu data: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading menu file: " + e.getMessage());
        }
    }
    public static void viewMenu() {
        Thread guiThread = new Thread(MenuPage::new);
        guiThread.setDaemon(true);
        guiThread.start();
    }
}