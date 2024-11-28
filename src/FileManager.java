//import java.io.*;
//import java.util.*;
//
//public class FileManager {
//
//    // Save customers to a text file
//    public void saveCustomersToTextFile(String filePath, List<Customer> customers) {
//        try (FileWriter writer = new FileWriter(filePath)) {
//            for (Customer customer : customers) {
//                writer.write("Username: " + customer.getUsername() + "\n");
//                writer.write("Password: " + customer.getPassword() + "\n");
//                writer.write("Order History:\n");
//                for (Order order : customer.getOrderHistory()) {
//                    writer.write("  - Item: " + order.getItemName() +
//                            ", Quantity: " + order.getQuantity() +
//                            ", Price: " + order.getPrice() + "\n");
//                }
//                writer.write("\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Load customers from a text file
//    public List<Customer> loadCustomersFromTextFile(String filePath) {
//        List<Customer> customers = new ArrayList<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            Customer currentCustomer = null;
//            List<Order> orderHistory = null;
//
//            while ((line = reader.readLine()) != null) {
//                if (line.startsWith("Username: ")) {
//                    if (currentCustomer != null) {
//                        currentCustomer.setOrderHistory(orderHistory);
//                        customers.add(currentCustomer);
//                    }
//                    currentCustomer = new Customer();
//                    currentCustomer.setUsername(line.substring(10));
//                    orderHistory = new ArrayList<>();
//                } else if (line.startsWith("Password: ")) {
//                    if (currentCustomer != null) {
//                        currentCustomer.setPassword(line.substring(10));
//                    }
//                } else if (line.startsWith("  - Item: ")) {
//                    String[] parts = line.split(", ");
//                    String itemName = parts[0].substring(10);
//                    int quantity = Integer.parseInt(parts[1].substring(10));
//                    double price = Double.parseDouble(parts[2].substring(7));
//                    orderHistory.add(new Order(itemName, quantity, price));
//                }
//            }
//            // Add the last customer
//            if (currentCustomer != null) {
//                currentCustomer.setOrderHistory(orderHistory);
//                customers.add(currentCustomer);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return customers;
//    }
//}
