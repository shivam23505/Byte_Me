import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MenuPage {
    JFrame frame;
    JTable menuTable;
    JButton viewOrdersButton;

    public MenuPage() {
        frame = new JFrame("Menu");

        String[] columns = {"Item Id","Item Name", "Price", "Quantity Available"};

        Object[][] data = new Object[Main.Menu.size()][4];
        int i = 0;
        for (FoodItem item : Main.Menu) {
            data[i][0] = item.getId();
            data[i][1] = item.getName();
            data[i][2] = item.getPrice();
            data[i][3] = item.getQuantity();
            i++;
        }
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);

        // Navigation button
        viewOrdersButton = new JButton("View Orders");
        viewOrdersButton.addActionListener(e -> {
            frame.dispose();
            new OrdersPage();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(scrollPane);
        panel.add(viewOrdersButton);

        frame.add(panel);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MenuPage();
    }
}
