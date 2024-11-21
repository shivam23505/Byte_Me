import java.awt.*;
import java.util.HashMap;
import java.util.Map;
/*
    RECEIVED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    DENIED
    CANCELED
 */
public class Order implements Comparable<Order> {
    private int OrderId;
    private Map<FoodItem,Integer>order;
    private int CustomerID;
    private String CustomerType; // VIP or REGULAR
    private String SpecialRequirements;
    private String SpecialRequirements_status;
    private String Status;
    private boolean isCancelled;

    public Order(int OrderId,Map<FoodItem,Integer> order, int CustomerID, String CustomerType,
                 String SpecialRequirements, String Status,String SpecialRequirements_status) {
        this.order = order;
        this.CustomerID = CustomerID;
        this.CustomerType = CustomerType;
        this.SpecialRequirements = SpecialRequirements;
        this.Status = Status;
        this.OrderId = OrderId;
        this.SpecialRequirements_status = SpecialRequirements_status;
        isCancelled = false;
    }
    public Order(int customerId,String CustomerType){
        order = new HashMap<>();
        this.CustomerID = customerId;
        this.CustomerType = CustomerType;
    }
    public Order(Order other){
        this.order = new HashMap<>();
        for (Map.Entry<FoodItem, Integer> entry : other.getOrder().entrySet()) {
            FoodItem item = entry.getKey();
            FoodItem copiedItem = new FoodItem(item.getId(),item.getName(),item.getCategory(),item.getPrice(),item.getQuantity());
            this.order.put(copiedItem, entry.getValue());
        }
        this.OrderId = other.getOrderId();
        this.CustomerID = other.getCustomerID();
        this.CustomerType = other.getCustomerType();
        this.SpecialRequirements = other.getSpecialRequirements();
        this.SpecialRequirements_status = other.getSpecialRequirements_status();
        this.Status = other.getStatus();
        isCancelled = other.getCancelled();
    }

    public void clear(){
        order.clear();
        setOrderId(-1);
        setSpecialRequirements(null);
        setSpecialRequirements_status(null);
        setStatus(null);
    }
    public void addItemInOrder(FoodItem fooditem, int quantity){
        order.put(fooditem,quantity);
    }
    public int removeItemFromOrder(FoodItem fooditem){
        int x = order.get(fooditem);
        order.remove(fooditem);
        return x;
    }
    public boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getSpecialRequirements_status() {
        return SpecialRequirements_status;
    }

    public void setSpecialRequirements_status(String specialRequirements_status) {
        SpecialRequirements_status = specialRequirements_status;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderID) {
        OrderId = orderID;
    }
    public Map< FoodItem,Integer> getOrder() {
        return order;
    }

    public void setOrder(Map<FoodItem,Integer> order) {
        this.order = order;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getSpecialRequirements() {
        return SpecialRequirements;
    }

    public void setSpecialRequirements(String specialRequirements) {
        SpecialRequirements = specialRequirements;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
    @Override
    public String toString() {
        return getOrderId()+ "("+getCustomerType()+ " ORDER) Status:" + getStatus();
    }

    @Override
    public int compareTo(Order other) {
        if (this.getCustomerType().equals("VIP") && !other.getCustomerType().equals("VIP")) return -1;
        if (!this.getCustomerType().equals("VIP") && other.getCustomerType().equals("VIP")) return 1;
        return Integer.compare(this.getOrderId(), other.getOrderId()); // Otherwise, compare by orderId
    }
    
    public void OrderDetails(){
//        System.out.println("ORDER DETAILS CALLED");
        for (FoodItem item : order.keySet()) {
            System.out.println("ItemId:" + item.getId() + "\tItem:" + item.getName() + "\tQuantity:" + order.get(item) + "\tPrice" + item.getPrice());
        }
    }
}
