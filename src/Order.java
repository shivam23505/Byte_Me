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
public class Order {
    private int OrderId;
    private Map<FoodItem,Integer>order;
    private String CustomerID;
    private String CustomerType; // VIP or REGULAR
    private String SpecialRequirements;
    private String SpecialRequirements_status;
    private String Status;
    private boolean isCancelled;

    public Order(int OrderId,Map<FoodItem,Integer> order, String CustomerID, String CustomerType,
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

    public boolean isCancelled() {
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

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
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
        return getOrderId()+ "("+getCustomerType() + " ORDER) Status:" + getStatus();
    }
    public void OrderDetails(){
        for (FoodItem item : getOrder().keySet()) {
            System.out.println("Item:" + item.getName() + "\tQuantity:" + getOrder().get(item));
        }
    }
}
