import java.util.HashMap;
import java.util.Map;

public class SalesReport {
    private double totalSales;
    private int totalOrders;
    private Map<FoodItem, Integer> itemSalesCount; // Tracks the number of times each item was ordered

    // Constructor
    public SalesReport() {
        totalSales = 0.0;
        totalOrders = 0;
        itemSalesCount = new HashMap<>();
    }

    // Methods to update sales data
    public void addOrderToReport(Order order) {
        totalOrders++;
//        order.getItems().forEach((item, quantity) -> {
//            itemSalesCount.put(item, itemSalesCount.getOrDefault(item, 0) + quantity);
//            totalSales += item.getPrice() * quantity;
//        });
    }

    public void refundBack(Order order){
        totalOrders--;
        for (Map.Entry<FoodItem, Integer> entry : order.getOrder().entrySet()) {
            FoodItem item = entry.getKey();
            Integer quantity = entry.getValue();
            //
            //Refund to the customer
            //Reduce from the sales
            //
        }
    }
    // Method to generate and display the report
    public void generateReport() {
        System.out.println("Daily Sales Report:");
        System.out.println("Total Sales: $" + totalSales);
        System.out.println("Total Orders: " + totalOrders);
        System.out.println("Most Popular Items:");
        itemSalesCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sort by sales count
                .forEach(entry -> System.out.println(entry.getKey().getName() + " - Sold: " + entry.getValue()));
    }
}
