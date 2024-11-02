public class FoodItem {
    private int id;
    private String name;
    private String category;
    private double price;
    private int available;

    public FoodItem(int id, String name, String category, double price, int available) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.available = available;
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
    public int isAvailable() {
        return available;
    }
    public void setAvailable(int available) {
        this.available = available;
    }
    @Override
    public String toString() {
        return id + ": " + name + " (" + category + ") - $" + price + (available>0 ? " [Available]" : " [Unavailable]");
    }

}
