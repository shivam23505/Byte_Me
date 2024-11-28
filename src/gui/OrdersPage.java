package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OrdersPage {
    JFrame frame;
    JTable ordersTable;
    JButton backButton;

    public OrdersPage() {
        frame = new JFrame("Orders");

        // Table setup
        String[] columns = {"Order Number", "Items Ordered", "Status"};
        Object[][] data = {
                {"#101", "Burger, Fries", "Preparing"},
                {"#102", "Pizza", "Out for Delivery"},
        };

        ordersTable = new JTable(new DefaultTableModel(data, columns));
        JScrollPane scrollPane = new JScrollPane(ordersTable);

        // Back button
        backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            // Navigate back to menu page
            frame.dispose();
            new MenuPage(); // Navigate back to MenuPage
        });

        // Layout setup
        JPanel panel = new JPanel();
        panel.add(scrollPane);
        panel.add(backButton);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
