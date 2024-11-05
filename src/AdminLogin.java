import java.util.List;
import java.util.Scanner;

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
        String name = scanner.next();
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

    public void AdminLogin(Scanner scanner,List<Admin>AllAdmin) {
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
                AdminMenu(scanner,new_admin);
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
                    AdminMenu(scanner,curr_admin);
                    running=false;
                }
                catch(InvalidLoginException e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
