import java.util.*;

public class Main {

    private static TreeSet<FoodItem> Menu = new TreeSet<>(new MenuIDComparator()); // Menu sorted by item ID
    private static PriorityQueue<Order> pendingOrders = new PriorityQueue<>(); // Orders sorted by priority (VIP first)
    //Delivered Orders
    //Pending Refunds Order(Cancelled Pending to Refund)
    private static List<Admin>Admins = new ArrayList<>();
    private static List<Customer>Customers = new ArrayList<>();
    private static List<Order>CancelledOrders;
    private static List<Order> AllOrders = new ArrayList<>();
    private static AdminLogin adminLogin = new AdminLogin();
    private static CustomerLogin customerLogin = new CustomerLogin();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("-------------------------------------------------");
            System.out.println("########### BYTE ME!! Food Delivery Application###########");
            System.out.println("1.Login as Customer");
            System.out.println("2.Login as Admin");
            System.out.println("5.Exit the application");
            System.out.println("-------------------------------------------------");
            System.out.print("Enter the choice:");

            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                customerLogin.CustomerLogin(scanner,Customers);
            } else if (choice == 2) {
                adminLogin.AdminLogin(scanner,Admins);
            } else if (choice == 3) {
                System.out.println("Thank you for using the application!!");
                running = false;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

}