import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OrdersPage {
    JFrame frame;
    JTable ordersTable;
    JButton backButton;

    public OrdersPage() {
        frame = new JFrame("PENDING ORDERS");
        String[] columns = {"Order Number", "Items Ordered", "Status"};


        Object[][] data = new Object[Main.AllOrders.size()][3];
        int i = 0;
        for (Order order : Main.AllOrders) {
            data[i][0] = order.getOrderId();
            data[i][1] = order.getOrder().size();
            data[i][2] = order.getStatus();
            i++;
        }
        ordersTable = new JTable(new DefaultTableModel(data, columns));
        JScrollPane scrollPane = new JScrollPane(ordersTable);

        backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuPage();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use vertical layout
        panel.add(scrollPane);
        panel.add(backButton);

        frame.add(panel);
        frame.setSize(500, 400); // Adjust size for better table visibility
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new OrdersPage();
    }
}
