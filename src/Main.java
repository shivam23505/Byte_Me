import java.util.*;

public class Main {
    private static Map<Integer, FoodItem>menu = new HashMap<>(); // Menu sorted by item ID
    private static PriorityQueue<Order> pendingOrders = new PriorityQueue<>(); // Orders sorted by priority (VIP first)
    //Delivered Orders
    //Pending Refunds Order(Cancelled Pending to Refund)
    private static List<Order> AllOrders = new ArrayList<>();
    public static void main(String[] args) {

    }
}