import jdk.jfr.Category;

import java.util.*;

public class Customer extends User{
    private List<Order> MyOrders;
    private int CustomerID;
    private Cart MyCart;
    private boolean isVIP;

    public Customer(String name, String address, String phone,int CustomerID,String email, String password,boolean isVIP) {
        super(name,phone,address,email,password);
        this.CustomerID = CustomerID;
        MyCart = new Cart();
        this.isVIP = isVIP;
        MyOrders = new ArrayList<Order>();
    }

    public List<Order> getMyOrders() {
        return MyOrders;
    }

    public void setMyOrders(List<Order> orderHistory) {
        MyOrders = orderHistory;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public int getCustomerID() {
        return CustomerID;
    }
    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }
    public Cart getMyCart() {
        return MyCart;
    }
    public void setMyCart(Cart myCart) {
        MyCart = myCart;
    }
    public boolean CartEmpty(){
        return MyCart == null;
    }
    public void BrowseMenu(Scanner scanner, Map<FoodItem,Integer> Menu){
        boolean running = true;
        while(running){
            System.out.println("1.View All Items");
            System.out.println("2.Search");
            System.out.println("3.Filter by Category");
            System.out.println("4.Sort by Price");
            System.out.println("5.Go Back");
            System.out.print("Enter the Choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                viewAllItems(Menu);
            }
            else if (choice == 2){
                searchItem(Menu,scanner);
            }
            else if (choice == 3){
                filterByCategory(Menu);
            }
            else if (choice == 4){
                sortItems(Menu,scanner);
            }
            else if (choice == 5){
                running = false;
            }
            else{
                System.out.println("INVALID INPUT!!");
            }
        }
    }
    public void viewAllItems(Map<FoodItem,Integer> Menu){
        System.out.println("Item\tQuantity\tPrice");
        for (FoodItem item : Menu.keySet()) {
            System.out.println(item.getName() + "\t" + Menu.get(item) + "\t" + item.getPrice());
        }
    }

    public  void searchItem(Map<FoodItem,Integer> Menu, Scanner scanner){
        System.out.print("ENTER THE KEYWORD TO SEARCH:");
        String keyword = scanner.nextLine();
        System.out.println("Item\tQuantity\tPrice");
        boolean ok = false;
        for (FoodItem item : Menu.keySet()) {
            if (item.getName().toLowerCase().contains(keyword.toLowerCase()) ||
            item.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                ok = true;
                System.out.println(item.getName() + "\t" + Menu.get(item) + "\t" + item.getPrice());
            }
        }
        if (!ok){
            System.out.println("No Item Found!!");
        }
    }
    public void filterByCategory(Map<FoodItem,Integer> Menu){
        Map<String,List<FoodItem>>Categories = new HashMap<>();
        for (FoodItem item : Menu.keySet()) {
            if (Categories.containsKey(item.getCategory())) {
                Categories.get(item.getCategory()).add(item);
            }
            else{
                List<FoodItem> list = new ArrayList<>();
                list.add(item);
                Categories.put(item.getCategory(), list);
            }
        }
        for (Map.Entry<String,List<FoodItem>> entry : Categories.entrySet()) {
            System.out.println("Category:" + entry.getKey());
            for (FoodItem item : entry.getValue()) {
                System.out.println("Name:" + item.getName() + "\tPrice:" + item.getPrice());
            }
            System.out.println();
        }
    }
    public void sortItems(Map<FoodItem,Integer> Menu, Scanner scanner){
        System.out.println("Enter 1 to sort in ascending order by price");
        System.out.println("Enter 2 to sort in descending order by price");
        System.out.print("Enter the choice:");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice!=1 && choice!=2){
            System.out.println("INVALID INPUT!!");
            return;
        }
        Map<FoodItem,Integer> currMenu = new HashMap<>(Menu);
        List<Map.Entry<FoodItem, Integer>> entryList = new ArrayList<>(currMenu.entrySet());

        entryList.sort((entry1, entry2) -> {
            Double price1 = entry1.getKey().getPrice();
            Double price2 = entry2.getKey().getPrice();
            return ((choice == 1)?price1.compareTo(price2):price2.compareTo(price1));
        });
        Map<FoodItem, Integer> sortedMenu = new LinkedHashMap<>();
        for (Map.Entry<FoodItem, Integer> entry : entryList) {
            sortedMenu.put(entry.getKey(), entry.getValue());
        }
        viewAllItems(sortedMenu);
    }

//    public void CartFunctions(){};
    public void OrderTracking(Scanner scanner,int orderID){
        boolean running = true;
        while(running) {
            System.out.println("1.View Order Status");
            System.out.println("2.Cancel My Order");
            System.out.println("3.View Order History");
            System.out.println("4.Go Back");
            System.out.print("Enter the choice");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                viewStatus(scanner);
            } else if (choice == 2) {
                cancelOrder(scanner);
            } else if (choice == 3) {
                viewHistory();
            }
            else if (choice == 4) {
                running = false;
            }
            else {
                System.out.println("INVALID INPUT!!");
            }
        }
    }
    public void viewStatus(Scanner scanner){
        System.out.print("Enter the OrderId:");
        int x = scanner.nextInt();
        scanner.nextLine();
        boolean found = false;
        Order temp = null;
        for (Order order:MyOrders) {
            if (order.getOrderId() == x){
                found = true;
                temp = order;
                break;
            }
        }
        if (found){
            System.out.println("ORDER STATUS:" + temp.getStatus());
            temp.OrderDetails();
        }
        else{
            System.out.println("INVALID ID!! ORDER NOT FOUND!!");
        }
    }
    public void cancelOrder(Scanner scanner){
        System.out.print("Enter the OrderId:");
        int x = scanner.nextInt();
        scanner.nextLine();
        boolean found = false;
        Order temp = null;
        for (Order order:MyOrders) {
            if (order.getOrderId() == x){
                found = true;
                temp = order;
                break;
            }
        }
        if (found){
            if (Objects.equals(temp.getStatus(), "DELIVERED")){
                System.out.println("CANNOT CANCEL!! ORDER HAS BEEN DELIVERED ALREADY");
            }
            else{
                temp.setCancelled(true);
                System.out.println("Order is Cancelled!!");
                System.out.println("Refund Process has begin!! Please visit the site regularly to get updates!!");
            }
        }
        else{
            System.out.println("INVALID ID!! ORDER NOT FOUND!!");
        }
    }
    public void viewHistory(){
        for (Order order:MyOrders){
            System.out.println("---");
            order.OrderDetails();
            System.out.println("---");
        }
    }

    public void Checkout(){};

    public void login(){
        System.out.println("Customer "+getName()+" has logged in");
    }
    public void logout(){
        System.out.println("Thank You for using out application!!");
        System.out.println("VISIT US AGAIN!!");
        System.out.println("Customer "+getName()+" has logged out.");
    }
}
