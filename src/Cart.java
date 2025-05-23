
import java.util.*;

public class Cart {
    private Customer myCustomer;
    private Order curr_order;
    private String status;
    private boolean payment;

    public Cart(int CustomerId, String CustomerType,Customer myCustomer) {
        curr_order = new Order(CustomerId, CustomerType);
        status = "EMPTY";
        this.myCustomer = myCustomer;
        payment = false;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getMyCustomer() {
        return myCustomer;
    }

    public FoodItem checkFunc(Scanner scanner, TreeSet<FoodItem>menu){
        int itemID = scanner.nextInt();
        scanner.nextLine();
        boolean found = false;
        FoodItem curr_item = null;
        for (FoodItem item:menu){
            if (item.getId() == itemID){
                curr_item = item;
                found = true;
                break;
            }
        }
        return curr_item;
    }
    public void CartOperations(Scanner scanner, TreeSet<FoodItem>menu,List<Order>AllOrders,List<Customer>allCustomers,PriorityQueue<Order>PendingOrders) {
        curr_order.setCustomerType((myCustomer.getVIP()?"VIP":"REGULAR"));
        boolean running = true;
        while(running){
            int newOrderID = 10 + AllOrders.size();
            System.out.println("-----------------------");
            System.out.println("1.ADD ITEM TO CART");
            System.out.println("2.REMOVE ITEM FROM CART");
            System.out.println("3.UPDATE MY ORDER");
            System.out.println("4.VIEW MY CART");
            System.out.println("5.CHECKOUT");
            System.out.println("6.EXIT");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1){
                addItem(scanner,menu,newOrderID);
            }
            else if (choice == 2){
                removeItem(scanner,menu);
            }
            else if (choice == 3){
                updateOrder(scanner,menu);
            }
            else if (choice == 4){
                viewCart();
            }
            else if (choice == 5){
                checkoutProcess(scanner,AllOrders,allCustomers,PendingOrders);
            }
            else if (choice == 6){
                running = false;
            }
            else{
                System.out.println("Invalid input");
            }
        }
    }
    public void addItem(Scanner scanner, TreeSet<FoodItem>menu,int newOrderID) {
        System.out.print("Enter the item ID to add:");
        FoodItem curr_item = checkFunc(scanner, menu);

        if (curr_item == null){
            System.out.println("Invalid ID! Item not found!");return;
        }
        System.out.println("Item selected:" + curr_item.getName());
        System.out.print("Enter the quantity:");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        if (quantity>curr_item.getQuantity()){
            System.out.println("Quantity exceeds current availability!! Please Reduce the quantity");
            return;
        }
        curr_item.setQuantity(curr_item.getQuantity()-quantity);
        curr_order.addItemInOrder(curr_item, quantity);
        curr_order.setOrderId(newOrderID);
//        curr_order.setStatus("RECEIVED");
        System.out.println("Item Successfully Added!!");
    }
    public String addItem(int itemId, int quantity, TreeSet<FoodItem>Menu, Order currOrder, int newOrderID) {
//        FoodItem currItem = Menu.stream().filter(item -> item.getId() == itemId).findFirst().orElse(null);
        FoodItem currItem = null;
        for (FoodItem item:Menu){
            if (item.getId() == itemId){
                currItem = item;
                break;
            }
        }
        if (currItem == null) {
            return "Invalid ID! Item not found!";
        }

        if (quantity > currItem.getQuantity()) {
            return "Quantity exceeds current availability! Please reduce the quantity.";
        }
        currItem.setQuantity(currItem.getQuantity() - quantity);
        currOrder.addItemInOrder(currItem, quantity);
        currOrder.setOrderId(newOrderID);
        return "Item successfully added!";
    }

    public void removeItem(Scanner scanner,  TreeSet<FoodItem>menu) {
        System.out.print("ENTER THE ITEM ID TO REMOVE:");
        FoodItem curr_item = checkFunc(scanner, menu);

        if (curr_item == null){
            System.out.println("Invalid ID! Item not found!");return;
        }
        System.out.println("Item selected:" + curr_item.getName());
        int x = curr_order.removeItemFromOrder(curr_item);
        curr_item.setQuantity(curr_item.getQuantity() + x);
        System.out.println("SUCCESSFULLY REMOVED ITEM FROM CART!!!");
    }
    public void updateOrder(Scanner scanner, TreeSet<FoodItem>menu) {
        System.out.print("Enter the item ID to update:");
        FoodItem curr_item = checkFunc(scanner, menu);

        if (curr_item == null){
            System.out.println("Invalid ID! Item not found!");
            return;
        }
        System.out.println("Item selected:" + curr_item.getName() + "\n-----");
        System.out.println("1.Update Quantity");
        System.out.println("2.Add special requirements");
        System.out.print("Enter your choice:");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1){
            System.out.print("Enter the new quantity:");
            int x = scanner.nextInt();
            scanner.nextLine();
            if (x>curr_item.getQuantity()){
                System.out.println("Quantity exceeds current availability!! Please Reduce the quantity");return;
            }
            curr_order.addItemInOrder(curr_item, x);
            curr_item.setQuantity(curr_item.getQuantity()-x);
            System.out.println("SUCCESSFULLY UPDATED ITEM FROM CART!!!");
        }
        else if (choice == 2){
            System.out.println("Enter the special requirement:");
            String require = scanner.nextLine();
            curr_order.setSpecialRequirements(require);
            System.out.println("SUCCESSFULLY UPDATED ITEM FROM CART!!!");
        }
        else{
            System.out.println("Invalid input");
        }
    }
    public void viewCart(){
        curr_order.OrderDetails();
    }
    public void checkoutProcess(Scanner scanner, List<Order> AllOrders,List<Customer>allCustomers,PriorityQueue<Order>PendingOrders) {
        double total = 0;
        for (FoodItem item:curr_order.getOrder().keySet()){
            total += item.getPrice() * curr_order.getOrder().get(item);
        }
        while(true) {
            System.out.println("YOUR TOTAL AMOUNT IS:" + total);
            System.out.println("-----PAYMENT WINDOW------");
            System.out.print("ENTER THE AMOUNT TO PAY(enter 0 to exit window):");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("PROCESSING PAYMENT!!!!!!!!");
            if (amount != total) {
                System.out.println("PAYMENT FAILED!! INVALID AMOUNT ENTERED!! TRY AGAIN");
            }
            else if (amount==0){
                break;
            }
            else{
                System.out.println("PAYMENT MADE SUCCESSFULLY!!");
                curr_order.setStatus("ORDER RECEIVED");
                Order new_order = new Order(curr_order);
//                System.out.println("CURRENT CUSTOMER IS :::::" + curr_order.getCustomerType());
                AllOrders.addLast(new_order);
                PendingOrders.add(new_order);
                System.out.println("ORDER PLACED SUCCESSFULLY!!");
                System.out.println("-----");
                System.out.println("ORDER ID:" + new_order.getOrderId());
                System.out.println("------");
                curr_order.OrderDetails();
                curr_order.clear();
                System.out.println("CURRENT STATUS:" + new_order.getStatus());
                myCustomer.addOrder(new_order);
                System.out.println("KEEP CHECKING THE SITE TO TRACK YOUR ORDER STATUS!!");
                System.out.println("THANK YOU!!");
                break;
            }
        }
    }

}
