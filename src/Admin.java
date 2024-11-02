import java.util.*;

public class Admin extends User{
    private SalesReport salesReport;
    public Admin(String name, String Phone, String Password,String email,String address) {
        super(name, Phone, Password, email, address);
        salesReport = new SalesReport();
    }

    public void addFoodItem(FoodItem item,Map<Integer, FoodItem> menu) {
        menu.put(item.getId(), item);
    }

    public void updateFoodItem(int id, double newPrice, int availability,Map<Integer, FoodItem> menu) {
        if (menu.containsKey(id)) {
            FoodItem item = menu.get(id);
            item.setPrice(newPrice);
            item.setAvailable(availability);
        }
    }

    public void removeFoodItem(int id,Map<Integer, FoodItem> menu,PriorityQueue<Order> pendingOrders) {
        if (menu.containsKey(id)) {
            menu.remove(id);
            // Update pending orders to 'DENIED' if they include this item
            for (Order order : pendingOrders) {
                Set<FoodItem> keys = order.getOrder().keySet();
                for (FoodItem item : keys) {
                    if (item.getId() == id) {
                        order.setStatus("DENIED!");
                    }
                }
            }
        }
    }
    // Order Management
    public void viewPendingOrders(PriorityQueue<Order> pendingOrders) {
        for (Order order : pendingOrders) {
            System.out.print("--");
            System.out.println(order);
            System.out.println();
        }
    }
    public void updateOrderStatus(int orderId, String status,PriorityQueue<Order> pendingOrders) {
        for (Order order : pendingOrders) {
            if (order.getOrderId() == orderId) {
                order.setStatus(status);
                if (status.equals("DELIVERED")) {
                    pendingOrders.remove(order);
                    salesReport.addOrderToReport(order);
                }
                break;
            }
        }
    }
    public void HandleSpecialRequests(int orderId,String status,PriorityQueue<Order> pendingOrders) {
        for (Order order : pendingOrders) {
            if (order.getOrderId() == orderId) {
                order.setSpecialRequirements_status(status);
            }
        }
    }

    public void processRefunds(int orderId, List<Order> AllOrders) {
        for (Order order : AllOrders) {
            if (order.getOrderId() == orderId) {
                if (order.getStatus() == "CANCELLED"){
                    salesReport.refundBack(order);
                    break;
                }
            }
        }
    }
}