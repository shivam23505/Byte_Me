package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MenuPage {
    JFrame frame;
    JTable menuTable;
    JButton viewOrdersButton;

    public MenuPage() {
        frame = new JFrame("Menu");

        // Table setup
        String[] columns = {"Item Name", "Price", "Available"};
        Object[][] data = {
                {"Burger", 50.0, "Yes"},
                {"Pizza", 100.0, "No"},
                {"Fries", 30.0, "Yes"}
        };

        menuTable = new JTable(new DefaultTableModel(data, columns));
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
        panel.add(scrollPane);
        panel.add(viewOrdersButton);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MenuPage();
    }
}
