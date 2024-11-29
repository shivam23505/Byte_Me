import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MenuPage {
    JFrame frame;
    JTable menuTable;
    JButton viewOrdersButton;

    public MenuPage() {
        frame = new JFrame("Menu");

        // Table setup
        String[] columns = {"Item Id","Item Name", "Price", "Quantity Available"};

        // Assuming Main.Menu is a List<FoodItem>
        Object[][] data = new Object[Main.Menu.size()][4]; // Dynamically allocate based on the number of items
        int i = 0;
        for (FoodItem item : Main.Menu) {
            data[i][0] = item.getId();
            data[i][1] = item.getName(); // Item name
            data[i][2] = item.getPrice(); // Item price
            data[i][3] = item.getQuantity(); // Item quantity
            i++;
        }
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);

        // Navigation button
        viewOrdersButton = new JButton("View Orders");
        viewOrdersButton.addActionListener(e -> {
            // Navigate to orders page
            frame.dispose();
            new OrdersPage(); // Assuming you have an OrdersPage class
        });

        // Layout setup
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use vertical layout
        panel.add(scrollPane);
        panel.add(viewOrdersButton);

        frame.add(panel);
        frame.setSize(500, 400); // Adjust size for better table visibility
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MenuPage();
    }
}
