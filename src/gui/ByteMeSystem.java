package gui;

import javax.swing.*;
import java.awt.*;

public class ByteMeSystem {
    JFrame frame;
    JPanel mainPanel;
    CardLayout cardLayout;

    public ByteMeSystem() {
        frame = new JFrame("Byte Me!");

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add pages
        mainPanel.add(createMenuPanel(), "Menu");
        mainPanel.add(createOrdersPanel(), "Orders");

        frame.add(mainPanel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Show menu initially
        cardLayout.show(mainPanel, "Menu");
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        JButton switchToOrders = new JButton("View Orders");
        switchToOrders.addActionListener(e -> cardLayout.show(mainPanel, "Orders"));
        panel.add(new JLabel("Menu Page"));
        panel.add(switchToOrders);
        return panel;
    }

    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel();
        JButton switchToMenu = new JButton("Back to Menu");
        switchToMenu.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        panel.add(new JLabel("Orders Page"));
        panel.add(switchToMenu);
        return panel;
    }

    public static void main(String[] args) {
        new ByteMeSystem();
    }
}

