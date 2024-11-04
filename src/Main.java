import java.util.*;

public class Main {

    private static TreeSet<FoodItem> Menu = new TreeSet<>(new MenuIDComparator()); // Menu sorted by item ID
    private static PriorityQueue<Order> pendingOrders = new PriorityQueue<>(); // Orders sorted by priority (VIP first)
    //Delivered Orders
    //Pending Refunds Order(Cancelled Pending to Refund)
    private static List<Customer>Customers = new ArrayList<>();
    private static List<Order>CancelledOrders;
    private static List<Order> AllOrders = new ArrayList<>();
    public static void main(String[] args) {

    }
}