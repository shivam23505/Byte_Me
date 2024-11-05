import java.util.*;

public class Admin extends User{
    private SalesReport salesReport;
    public Admin(String name, String Phone, String Password,String email) {
        super(name, Phone, null, email, Password);
        salesReport = new SalesReport();
    }

    public SalesReport getSalesReport() {
        return salesReport;
    }

    public void setSalesReport(SalesReport salesReport) {
        this.salesReport = salesReport;
    }
    public FoodItem checkITEM(Scanner scanner,TreeSet<FoodItem>menu){
        System.out.print("ENTER THE ITEM ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        boolean found = false;
        FoodItem foodItem = null;
        for (FoodItem entry:menu){
            if (entry.getId()==id){
                foodItem = entry;
                found = true;
                break;
            }
        }
        return foodItem;
    }
    public void AdminFunctions(Scanner scanner,TreeSet<FoodItem>menu,List<Order>AllOrders,PriorityQueue<Order>PendingOrders){
        boolean running = true;
        while(running){
            System.out.println("1.MENU MANAGEMENT");
            System.out.println("2.ORDER MANAGEMENT");
            System.out.println("3.REPORT GENERATION");
            System.out.println("4.EXIT");
            System.out.print("Enter your choice:");
            int x = scanner.nextInt();
            scanner.nextLine();
            if (x==1){
                menuManagement(scanner,menu,PendingOrders);
            }
            else if (x==2){
                OrderManagement(scanner,AllOrders,PendingOrders);
            }
            else if (x==3){
                reportGenerator();
            }
            else if (x==4){
                running = false;
            }
            else{
                System.out.println("Invalid choice");
            }
        }
    }

    public void menuManagement(Scanner scanner,TreeSet<FoodItem>menu,PriorityQueue<Order>PendingOrders){
        boolean running = true;
        while(running){
            System.out.println("1.ADD NEW ITEMS");
            System.out.println("2.UPDATE EXISTING ITEMS");
            System.out.println("3.REMOVE ITEM");
            System.out.println("4.EXIT");
            System.out.print("Enter your choice:");
            int x = scanner.nextInt();
            scanner.nextLine();
            if (x==1){
                addFoodItem(scanner,menu);
            }
            else if (x==2){
                updateFoodItem(scanner,menu);
            }
            else if (x==3){
                removeFoodItem(scanner,menu,PendingOrders);
            }
            else if (x==4){
                running = false;
            }
            else{
                System.out.println("Invalid choice");
            }
        }
    }
    public void addFoodItem(Scanner scanner,TreeSet<FoodItem>menu) {
        System.out.print("ENTER THE ITEM ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("ENTER THE ITEM NAME:");
        String name = scanner.nextLine();
        System.out.print("ENTER THE ITEM CATEGORY:");
        String category = scanner.nextLine();
        System.out.print("ENTER THE ITEM PRICE:");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("ENTER THE QUANTITY IN MENU:");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        FoodItem foodItem = new FoodItem(id,name,category,price,quantity);
        menu.add(foodItem);
        System.out.println("ITEM SUCCESSFULLY ADDED IN THE MENU!!!");
    }

    public void updateFoodItem(Scanner scanner, TreeSet<FoodItem> menu) {
        FoodItem foodItem = checkITEM(scanner,menu);
        if (foodItem==null){
            System.out.println("ITEM NOT FOUND");return;
        }
        foodItem.updateDetails(scanner);
    }


    public void removeFoodItem(Scanner scanner, TreeSet<FoodItem> menu, PriorityQueue<Order> pendingOrders) {
        System.out.println("ENTER THE ITEM ID TO REMOVE:");
        int id = scanner.nextInt();
        scanner.nextLine();
        boolean found=false;
        Iterator<FoodItem> iterator = menu.iterator();
        while (iterator.hasNext()) {
            FoodItem item = iterator.next();
            if (item.getId() == id) {
                found = true;
                iterator.remove();
                // Update pending orders
                for (Order order : pendingOrders) {
                    Set<FoodItem> keys = order.getOrder().keySet();
                    for (FoodItem item2 : keys) {
                        if (item2.getId() == id) {
                            order.setStatus("DENIED!"); // Update order status
                        }
                    }
                }
                break;
            }
        }
        if (!found){
            System.out.println("ITEM NOT FOUND");return;
        }
        System.out.println("ITEM SUCCESSFULLY REMOVED IN THE MENU!!!");
    }

    public void OrderManagement(Scanner scanner,List<Order>AllOrders,PriorityQueue<Order>PendingOrders) {
        boolean running = true;
        while(running){
            System.out.println("1.VIEW PENDING ORDERS");
            System.out.println("2.PROCESS ORDER");
            System.out.println("3.UPDATE ORDER STATUS");
            System.out.println("4.PROCESS REFUNDS");
            System.out.println("5.HANDLE SPECIAL REQUESTS");
            System.out.println("6.EXIT");
            System.out.print("Enter your choice:");
            int x = scanner.nextInt();
            scanner.nextLine();
            if (x==1){
                viewPendingOrders(PendingOrders);
            }
            else if (x==2){
                processOrder(PendingOrders);
            }
            else if (x==3){
                updateOrderStatus(scanner,PendingOrders);
            }
            else if (x==4){
                processRefunds(scanner,AllOrders);
            }
            else if (x==5){
                handleRequests(scanner,PendingOrders);
            }
            else if (x==6){
                running = false;
            }
            else{
                System.out.println("Invalid choice");
            }
        }
    }

    // Order Management
    public void viewPendingOrders(PriorityQueue<Order> pendingOrders) {
        for (Order order : pendingOrders) {
            System.out.print("--");
            System.out.println(order);
            order.OrderDetails();
            System.out.println();
        }
    }
    public void processOrder(PriorityQueue<Order>PendingOrders){
        Order top_order = PendingOrders.poll();
        if (top_order == null){
            System.out.println("THERE ARE NO PENDING ORDERS CURRENTLY!!!");
            return;
        }
        top_order.setStatus("DELIVERED");
        System.out.println("ORDER ID:" + top_order.getOrderId() + " has been delivered!!");
        System.out.println("ORDER DETAILS ARE:");
        top_order.OrderDetails();
    }

    public void updateOrderStatus(Scanner scanner,PriorityQueue<Order> pendingOrders) {
        System.out.print("Enter the order id:");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the new order status:");
        String newStatus = scanner.nextLine();
        boolean found = false;
        for (Order order : pendingOrders) {
            if (order.getOrderId() == orderId) {
                found = true;
                order.setStatus(newStatus);
                if (newStatus.equals("DELIVERED")) {
                    pendingOrders.remove(order);
                    salesReport.addOrderToReport(order);
                }
                break;
            }
        }
        if (!found){
            System.out.println("ORDER NOT FOUND");
            return;
        }
        System.out.println("STATUS UPDATED SUCCESSFULLY!!");
    }
    public void handleRequests(Scanner scanner, PriorityQueue<Order> pendingOrders) {
        System.out.print("Enter the order id to handle special request:");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        boolean found = false;
        for (Order order : pendingOrders) {
            if (order.getOrderId() == orderId) {
                found = true;
                order.setSpecialRequirements_status("HANDLED");
                System.out.println("Special request has been handled for order id:" + orderId);
                break;
            }
        }
        if (!found){
            System.out.println("ORDER NOT FOUND");
        }
    }
    public void processRefunds(Scanner scanner, List<Order> AllOrders) {
        System.out.print("Enter the order id to start process refund:");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        boolean found = false;
        for (Order order : AllOrders) {
            if (order.getOrderId() == orderId) {
                if (Objects.equals(order.getStatus(), "CANCELLED")){
                    found = true;
                    salesReport.refundBack(order);
                    break;
                }
            }
        }
        if (!found){
            System.out.println("ORDER NOT FOUND");
        }
    }
    public void login(){
        System.out.println("Admin "+getName()+" has logged in");
    }
    public void reportGenerator(){
        salesReport.generateReport();
    }
}