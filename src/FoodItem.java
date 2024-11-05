import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodItem {
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private List<String> Reviews;

    public FoodItem(int id, String name, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        Reviews = new ArrayList<String>();
    }

    public List<String> getReviews() {
        return Reviews;
    }

    public void setReviews(List<String> reviews) {
        Reviews = reviews;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void updateDetails(Scanner scanner){
        boolean running = true;
        while(running){
            System.out.println("1.UPDATE PRICE");
            System.out.println("2.UPDATE QUANTITY");
            System.out.println("3.UPDATE CATEGORY");
            System.out.println("4.EXIT");
            System.out.print("Enter your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1){
                System.out.print("Enter the new price:");
                double newPrice = scanner.nextDouble();
                scanner.nextLine();
                setPrice(newPrice);
                running = false;
            }
            else if (choice == 2){
                System.out.print("Enter the new quantity:");
                int newQuantity = scanner.nextInt();
                scanner.nextLine();
                setQuantity(newQuantity);
            }
            else if (choice == 3){
                System.out.print("Enter the new category:");
                String newCategory = scanner.nextLine();
                setCategory(newCategory);
            }
            else if (choice == 4){
                running = false;
            }
            else{
                System.out.println("Invalid choice");
            }
        }
        System.out.println("DETAILS UPDATED SUCCESSFULLY!!!");
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FoodItem foodItem = (FoodItem) obj;
        return getId() == foodItem.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getId()) ;
    }

    @Override
    public String toString() {
        return id + ": " + name + " (" + category + ") - $" + price + (getQuantity()>0 ? " [Available]" : " [Unavailable]");
    }
    public void addComment(String comment){
        Reviews.add(comment);
    }
    public void viewMyReview(){
        System.out.println("Reviews for " + getName()+":");
        for (String comment:getReviews()){
            System.out.println("--" + comment);
        }
    }

}
