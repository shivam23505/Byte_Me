import java.util.HashMap;
import java.util.Map;

public class SalesReport {
    private double totalSales;
    private double refunded;
    private int OrdersSold;
    private int OrdersCancelled;
    private Map<FoodItem, Integer> itemSalesCount; // Tracks the number of times each item was ordered

    // Constructor
    public SalesReport() {
        totalSales = 0.0;
        OrdersSold = 0;
        refunded = 0.0;
        OrdersCancelled = 0;
        itemSalesCount = new HashMap<>();
    }

    // Methods to update sales data
    public void addOrderToReport(Order order) {
        OrdersSold++;
        for (Map.Entry<FoodItem,Integer>entry:order.getOrder().entrySet()){
            if (itemSalesCount.containsKey(entry.getKey())) {
                itemSalesCount.put(entry.getKey(), itemSalesCount.get(entry.getKey()) + entry.getValue());
            }
            else {
                itemSalesCount.put(entry.getKey(), entry.getValue());
            }
            totalSales += entry.getKey().getPrice()*entry.getValue();
        }
    }

    public void refundBack(Order order){
        OrdersCancelled++;
        OrdersSold--;
        for (Map.Entry<FoodItem, Integer> entry : order.getOrder().entrySet()) {
            FoodItem item = entry.getKey();
            Integer quantity = entry.getValue();
            refunded += item.getPrice()*quantity;
        }
    }
    // Method to generate and display the report
    public void generateReport() {
        System.out.println("Daily Sales Report:");
        System.out.println("Total Sales: $" + totalSales);
        System.out.println("Total Orders sold: " + OrdersSold);
        System.out.println("Total Orders cancelled: " + OrdersCancelled);
        System.out.println("Most Popular Items:");
        itemSalesCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sort by sales count
                .forEach(entry -> System.out.println(entry.getKey().getName() + " - Sold: " + entry.getValue()));
    }
}
