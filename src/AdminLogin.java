import com.sun.source.tree.Tree;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

public class AdminLogin {
    public Admin checkAdminLogin(String email,String password,List<Admin>AllAdmin) throws InvalidLoginException{
        for (Admin s:AllAdmin){
            if (s.getEmail().equals(email) && s.getPassword().equals(password)){
                return s;
            }
        }
        throw new InvalidLoginException("Invalid login!!");
    }

    public Admin AdminSignup(Scanner scanner,List<Admin>AllAdmins){
        System.out.print("Enter your name:");
        String name = scanner.nextLine();
        System.out.print("Enter your email:");
        String email = scanner.nextLine();
        System.out.print("Enter your password:");
        String password = scanner.nextLine();
        System.out.print("Enter your contact:");
        String phone = scanner.nextLine();
        for(Admin s:AllAdmins){
            if (s.getEmail().equals(email)){
                System.out.println("Invalid Details. Email already exists!!");
                return null;
            }
        }
        Admin admin = new Admin(name,phone,password,email );
        AllAdmins.add(admin);
        return admin;
    }

    public void loginAdmin(Scanner scanner, List<Admin>AllAdmin, TreeSet<FoodItem>Menu,List<Order>AllOrder,PriorityQueue<Order>PendingOrder) {
        boolean running = true;
        while(running) {
            System.out.println("-------------------------------------------------");
            System.out.println("1.Admin Signup");
            System.out.println("2.Admin login");
            System.out.println("3.Go Back");
            System.out.println("-------------------------------------------------");
            System.out.print("Enter the choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                Admin new_admin = AdminSignup(scanner,AllAdmin);
                if(new_admin == null){continue;}
                System.out.println("Admin " + new_admin.getEmail() + " successfully signed in!!");
                AdminMenu(scanner,new_admin,Menu,AllOrder,PendingOrder);
                running=false;
            } else if (choice == 3) {
                running = false;
            } else if (choice == 2) {
                System.out.print("Enter your email:");
                String email = scanner.nextLine();
                System.out.print("Enter your password:");
                String password = scanner.nextLine();
                try{
                    Admin curr_admin = checkAdminLogin(email,password,AllAdmin);
                    curr_admin.login();
                    AdminMenu(scanner,curr_admin,Menu,AllOrder,PendingOrder);
                    running=false;
                }
                catch(InvalidLoginException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    public void AdminMenu(Scanner scanner, Admin admin, TreeSet<FoodItem> menu, List<Order>AllOrders, PriorityQueue<Order> PendingOrders){
        boolean running = true;
        while(running){
            System.out.println("---------------------");
            System.out.println("1.MENU MANAGEMENT");
            System.out.println("2.ORDER MANAGEMENT");
            System.out.println("3.REPORT GENERATION");
            System.out.println("4.EXIT");
            System.out.print("Enter your choice:");
            int x = scanner.nextInt();
            scanner.nextLine();
            if (x==1){
                admin.menuManagement(scanner,menu,PendingOrders);
            }
            else if (x==2){
                admin.OrderManagement(scanner,AllOrders,PendingOrders);
            }
            else if (x==3){
                admin.reportGenerator();
            }
            else if (x==4){
                running = false;
            }
            else{
                System.out.println("Invalid choice");
            }
        }
    }
}
