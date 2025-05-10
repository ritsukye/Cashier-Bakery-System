import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeePage {

    private static final String URL = "jdbc:mysql://localhost:3306/Bakery";
    private static final String USER = "root";
    private static final String PASSWORD = "bakery2025";

    static class OrderItem {
        int productId;
        String name;
        int quantity;
        double unitPrice;

        OrderItem(int productId, String name, int quantity, double unitPrice) {
            this.productId = productId;
            this.name = name;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        double getTotal() {
            return quantity * unitPrice;
        }
    }

    static List<OrderItem> cart = new ArrayList<>();

    public static void main(String[] args) {
        showEmployeePage();
    }

    public static void showEmployeePage() {
        JFrame frame = new JFrame("Employee Page");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(null);
        placeComponents(panel);
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        JButton placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.setBounds(200, 200, 200, 30);
        panel.add(placeOrderBtn);

        JButton viewOders = new JButton("View Orders");
        viewOders.setBounds(200, 250, 200, 30);
        panel.add(viewOders);

        JButton backButton = new JButton("Back to Main Page");
        backButton.setBounds(200, 300, 200, 30);
        panel.add(backButton);

        

        placeOrderBtn.addActionListener(e -> showProductSelection());
        viewOders.addActionListener(e-> showOrdersTable()); 
        backButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            topFrame.dispose();
            MainPage.main(null); 
        });
    }

    private static void showOrdersTable() {
    JFrame frame = new JFrame("All Orders");
    frame.setSize(800, 400);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);

    model.addColumn("Order ID");
    model.addColumn("Customer Name");
    model.addColumn("Product ID");
    model.addColumn("Quantity");
    model.addColumn("Total Price");

    try (Connection conn = getConnection()) {
        String sql = "SELECT o.order_id, c.name, o.product_id, o.quantity, o.total_price " +
                     "FROM Orders o JOIN Customers c ON o.customer_id = c.customer_id";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("order_id"),
                rs.getString("name"),
                rs.getInt("product_id"),
                rs.getInt("quantity"),
                rs.getInt("total_price")
            });
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
    }

    frame.add(new JScrollPane(table));
    frame.setVisible(true);
    }


    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

   

    private static void showProductSelection() {
    java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis()); // get system date
    JFrame frame = new JFrame("Checkout");
    frame.setSize(900, 500);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    DefaultTableModel productModel = new DefaultTableModel();
    JTable productTable = new JTable(productModel);

    try (Connection conn = getConnection()) {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            productModel.addColumn(meta.getColumnName(i));
        }

        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getObject(i + 1);
            }
            productModel.addRow(row);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
    }

    JButton addToCartBtn = new JButton("Add to Cart");
    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
    leftPanel.add(addToCartBtn, BorderLayout.SOUTH);

    DefaultTableModel cartModel = new DefaultTableModel(new String[]{"Product", "Qty", "Unit Price", "Total"}, 0);
    JTable cartTable = new JTable(cartModel);
    JLabel totalLabel = new JLabel("Total: $0.00");
    JButton checkoutBtn = new JButton("Checkout");
    JButton deleteBtn = new JButton("Delete");
    JButton updateBtn = new JButton("Update");

    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

    JPanel bottomRight = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.add(updateBtn);
    buttonPanel.add(deleteBtn);
    buttonPanel.add(checkoutBtn);

    bottomRight.add(totalLabel, BorderLayout.WEST);
    bottomRight.add(buttonPanel, BorderLayout.EAST);
    rightPanel.add(bottomRight, BorderLayout.SOUTH);

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
    splitPane.setDividerLocation(450);
    frame.add(splitPane);

    frame.setVisible(true);

    addToCartBtn.addActionListener(e -> {
        int row = productTable.getSelectedRow();
        if (row == -1) return;

        int productId = (int) productTable.getValueAt(row, 0);
        String name = productTable.getValueAt(row, 1).toString();
        int stock = Integer.parseInt(productTable.getValueAt(row, 2).toString());
        double price = Double.parseDouble(productTable.getValueAt(row, 3).toString());

        String qtyStr = JOptionPane.showInputDialog("Enter quantity for " + name + ":");
        try {
            int qty = Integer.parseInt(qtyStr.trim());
            if (qty <= 0 || qty > stock) {
                JOptionPane.showMessageDialog(frame, "Invalid quantity.");
                return;
            }

            cart.add(new OrderItem(productId, name, qty, price));
            cartModel.addRow(new Object[]{name, qty, price, qty * price});

            double total = 0;
            for (OrderItem item : cart) {
                total += item.getTotal();
            }
            totalLabel.setText("Total: $" + String.format("%.2f", total));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid number.");
        }
    });

    checkoutBtn.addActionListener(e -> {
        String customerName = JOptionPane.showInputDialog("Enter customer name:");
        if (customerName == null || customerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Customer name required.");
            return;
        }

        int customerId = (int) (Math.random() * 10000); // Random ID between 0–9999

        try (Connection conn = getConnection()) {
            PreparedStatement insertStmt = conn.prepareStatement(
                "INSERT INTO Customers (customer_id, name) VALUES (?, ?)");
            insertStmt.setInt(1, customerId);
            insertStmt.setString(2, customerName);
            insertStmt.executeUpdate();

            for (OrderItem item : cart) {
                PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE Product SET inventory = inventory - ? WHERE product_id = ?");
                updateStmt.setInt(1, item.quantity);
                updateStmt.setInt(2, item.productId);
                updateStmt.executeUpdate();

                PreparedStatement orderStmt = conn.prepareStatement(
                    "INSERT INTO Orders (customer_id, product_id, quantity, total_price, date) VALUES (?, ?, ?, ?, ?)");
                orderStmt.setInt(1, customerId);
                orderStmt.setInt(2, item.productId);
                orderStmt.setInt(3, item.quantity);
                orderStmt.setDouble(4, item.getTotal());
                orderStmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                orderStmt.executeUpdate();
            }

            cart.clear();
            cartModel.setRowCount(0);
            totalLabel.setText("Total: $0.00");
            JOptionPane.showMessageDialog(frame, "Order for " + customerName + " completed!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Checkout error: " + ex.getMessage());
        }
    });

    deleteBtn.addActionListener(e -> {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow != -1) {
            cart.remove(selectedRow); // remove from data model
            cartModel.removeRow(selectedRow); // remove from table
            double total = cart.stream().mapToDouble(OrderItem::getTotal).sum();
            totalLabel.setText("Total: $" + String.format("%.2f", total));
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an item to delete.");
        }
    });

    updateBtn.addActionListener(e -> {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow != -1) {
            String newQtyStr = JOptionPane.showInputDialog("Enter new quantity:");
            try {
                int newQty = Integer.parseInt(newQtyStr.trim());
                if (newQty <= 0) {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity.");
                    return;
                }

                OrderItem item = cart.get(selectedRow);
                item.quantity = newQty;
                cartModel.setValueAt(newQty, selectedRow, 1);
                cartModel.setValueAt(item.getTotal(), selectedRow, 3);

                double total = cart.stream().mapToDouble(OrderItem::getTotal).sum();
                totalLabel.setText("Total: $" + String.format("%.2f", total));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid number.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select an item to update.");
        }
    });
}

}
