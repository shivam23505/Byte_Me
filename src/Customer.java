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
        MyCart = new Cart(getCustomerID(),(isVIP?"VIP":"REGULAR"),this);
        this.isVIP = isVIP;
        MyOrders = new ArrayList<Order>();
    }
    public void addOrder(Order order){
        MyOrders.add(order);
    }
    public List<Order> getMyOrders() {
        return MyOrders;
    }

    public void setMyOrders(List<Order> orderHistory) {
        MyOrders = orderHistory;
    }

    public boolean getVIP() {
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
    public void BrowseMenu(Scanner scanner, TreeSet<FoodItem> Menu){
        boolean running = true;
        while(running){
            System.out.println("-------------------------");
            System.out.println("1.View All Items");
            System.out.println("2.Search");
            System.out.println("3.Filter by Category");
            System.out.println("4.Sort by Price");
            System.out.println("5.View Item Review");
            System.out.println("6.Go Back");
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
                viewItemReview(scanner,Menu);
            }
            else if (choice == 6){
                running = false;
            }
            else{
                System.out.println("INVALID INPUT!!");
            }
        }
    }
    public void viewAllItems(TreeSet<FoodItem> menu) {
        if (menu.isEmpty()){
            System.out.println("MENU IS EMPTY!!");
            return;
        }
        System.out.println("ItemId\tItem\tQuantity(AVAILABLE)\tPrice");
        for (FoodItem item : menu) {
            System.out.println(item.getId() + "\t" + item.getName() + "\t" + item.getQuantity() + "\t" + item.getPrice());
        }
    }

    public  void searchItem(TreeSet<FoodItem> Menu, Scanner scanner){
        System.out.print("ENTER THE KEYWORD TO SEARCH:");
        String keyword = scanner.nextLine();
        System.out.println("ItemId\tItem\tQuantity\tPrice");
        boolean ok = false;
        for (FoodItem item : Menu) {
            if (item.getName().toLowerCase().contains(keyword.toLowerCase()) ||
            item.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                ok = true;
                System.out.println(item.getId() + "\t" + item.getName() + "\t" + item.getQuantity() + "\t" + item.getPrice());
            }
        }
        if (!ok){
            System.out.println("No Item Found!!");
        }
    }
    public void filterByCategory(TreeSet<FoodItem> Menu){
        Map<String,List<FoodItem>>Categories = new HashMap<>();
        for (FoodItem item : Menu) {
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
                System.out.println("ID:" + item.getId() + "\tName:" + item.getName() + "\tPrice:" + item.getPrice());
            }
            System.out.println();
        }
    }
    public void sortItems(TreeSet<FoodItem> Menu, Scanner scanner){
        System.out.println("Enter 1 to sort in ascending order by price");
        System.out.println("Enter 2 to sort in descending order by price");
        System.out.print("Enter the choice:");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice!=1 && choice!=2){
            System.out.println("INVALID INPUT!!");
            return;
        }
        Comparator<FoodItem> AsecPriceComparator = new Comparator<>() {
            @Override
            public int compare(FoodItem entry1,FoodItem entry2) {
                return Double.compare(entry1.getPrice(), entry2.getPrice());
            }
        };

        Comparator<FoodItem> DescPriceComparator = new Comparator<>() {
            @Override
            public int compare(FoodItem entry1,FoodItem entry2) {
                return Double.compare(entry2.getPrice(), entry1.getPrice());
            }
        };

        TreeSet<FoodItem>sortedMenu;
        if (choice == 1){
            sortedMenu = new TreeSet<>(AsecPriceComparator);
        }
        else{
            sortedMenu = new TreeSet<>(DescPriceComparator);
        }
        sortedMenu.addAll(Menu);
        viewAllItems(sortedMenu);
    }
    public void PLACE_ORDER(Scanner scanner,TreeSet<FoodItem>menu,List<Order>AllOrders,List<Customer>AllCustomers,PriorityQueue<Order>PendingOrder){
        getMyCart().CartOperations(scanner,menu,AllOrders,AllCustomers,PendingOrder);
    }
    public void ObtainMembership(Scanner scanner){
        if (getVIP()){
            System.out.println("YOU ARE ALREADY A VIP MEMBER!!");
            return;
        }
        boolean running = true;
        while(running){
            System.out.println("-----------------------------");
            System.out.println("VIP MEMBERSHIP COST:5000");
            System.out.println("1.Obtain Membership");
            System.out.println("2.Exit");
            System.out.print("Enter your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1){
                System.out.println("CONGRATULATIONS!! YOU ARE NOW A VIP MEMBER!!");
                setVIP(true);

                running = false;
            }
            else if (choice == 2){
                running = false;
            }
            else{
                System.out.println("INVALID INPUT!!");
            }
        }
    }
    public void OrderTracking(Scanner scanner,List<Order>CancelledOrders,PriorityQueue<Order>PendingOrders){
        boolean running = true;
        while(running) {
            System.out.println("-----------------------------");
            System.out.println("1.View Order Status");
            System.out.println("2.Cancel My Order");
            System.out.println("3.View My Previous Orders");
            System.out.println("4.Go Back");
            System.out.print("Enter the choice");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                viewStatus(scanner);
            } else if (choice == 2) {
                cancelOrder(scanner,CancelledOrders,PendingOrders);
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
    public void cancelOrder(Scanner scanner,List<Order>CancelledOrder,PriorityQueue<Order>PendingOrders){
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
        if (!found){
            System.out.println("INVALID ID!! ORDER NOT FOUND!!");return;
        }
        if (Objects.equals(temp.getStatus(), "DELIVERED")){
            System.out.println("CANNOT CANCEL!! ORDER HAS BEEN DELIVERED ALREADY");
        }
        else{
            temp.setCancelled(true);
            temp.setStatus("CANCELLED");
            System.out.println("Order is Cancelled!!");
            System.out.println("Refund Process has begin!! Please visit the site regularly to get updates!!");
            CancelledOrder.add(temp);

            Iterator<Order> iterator = PendingOrders.iterator();
            while (iterator.hasNext()) {
                Order curr = iterator.next();
                if (curr.getOrderId() == x) {
                    iterator.remove();
                }
            }
        }
    }
    public void viewHistory(){
        for (Order order:MyOrders){
            System.out.println("---");
            order.OrderDetails();
            System.out.println("---");
        }
    }
    public void viewItemReview(Scanner scanner, TreeSet<FoodItem>Menu){
        System.out.print("Enter the item id to view review:");
        int x = scanner.nextInt();
        scanner.nextLine();
        FoodItem temp = null;
        boolean found = false;
        for (FoodItem f:Menu){
            if (f.getId() == x){
                found = true;temp = f;
                break;
            }
        }
        if (!found){
            System.out.println("Invalid ID!!");return;
        }
        temp.viewMyReview();
    }
    public void giveComment(Scanner scanner, TreeSet<FoodItem>Menu){
        System.out.print("Enter the item id to give review:");
        int x = scanner.nextInt();
        scanner.nextLine();
        FoodItem temp = null;
        boolean found = false;
        for (FoodItem f:Menu){
            if (f.getId() == x){
                found = true;temp = f;
                break;
            }
        }
        if (!found){
            System.out.println("Invalid ID!!");return;
        }
        System.out.print("Enter the review:");
        String comment = scanner.nextLine();
        temp.addComment(comment);
        System.out.println("REVIEW ADDED SUCCESSFULLY!!");
    }

    public void login(){
        System.out.println("Customer "+getName()+" has logged in");
    }
    public void logout(){
        System.out.println("Thank You for using out application!!");
        System.out.println("VISIT US AGAIN!!");
        System.out.println("Customer "+getName()+" has logged out.");
    }
}
