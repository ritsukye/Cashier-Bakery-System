import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class EmployeePage {

    private static final String URL = "jdbc:mysql://localhost:3306/Bakery";
    private static final String USER = "root";
    private static final String PASSWORD = "bakery2025";

    public static void main(String[] args) {
        showEmployeePage();
    }

    public static void showEmployeePage() {
        JFrame frame = new JFrame("Employee Page");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(null);
        placeComponents(panel);
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        JButton viewProductsBtn = new JButton("View Baked Goods");
        viewProductsBtn.setBounds(50, 30, 200, 30);
        panel.add(viewProductsBtn);

        JButton placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.setBounds(50, 80, 200, 30);
        panel.add(placeOrderBtn);

        JButton backButton = new JButton("Back to Main Page");
        backButton.setBounds(50, 130, 200, 30);
        panel.add(backButton);

        viewProductsBtn.addActionListener(e -> openTable("Product"));
        placeOrderBtn.addActionListener(e -> placeOrder());
        backButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            topFrame.dispose();
            MainPage.main(null);
        });
    }

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void openTable(String type) {
        //set windows,,,these dont fully work yet but im ab to sleep
        JFrame frame = new JFrame("Available Products");
        frame.setSize(600, 400);
        JPanel panel = new JPanel(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        //connect to db, im getting err with db connection.java rn tho
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(meta.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }

        //table display for when ths works
        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void placeOrder() {
        //window layout
        JFrame frame = new JFrame("Place Order");
        frame.setSize(400, 400);
        JPanel panel = new JPanel(new GridLayout(6, 2));

        //order info, also need to fix this
        JTextField customerIdField = new JTextField();
        JTextField employeeIdField = new JTextField();
        JTextField productIdField = new JTextField();
        JTextField quantityField = new JTextField();

        JButton submitBtn = new JButton("Submit Order");

        panel.add(new JLabel("Customer ID:"));
        panel.add(customerIdField);
        panel.add(new JLabel("Employee ID:"));
        panel.add(employeeIdField);
        panel.add(new JLabel("Product ID:"));
        panel.add(productIdField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(submitBtn);

        frame.add(panel);
        frame.setVisible(true);

        //bugged sbmit
        submitBtn.addActionListener(e -> {
            try (Connection conn = getConnection()) {
                int productId = Integer.parseInt(productIdField.getText().trim());
                int quantity = Integer.parseInt(quantityField.getText().trim());

                PreparedStatement priceStmt = conn.prepareStatement("SELECT price FROM Product WHERE product_id = ?");
                priceStmt.setInt(1, productId);
                ResultSet rs = priceStmt.executeQuery();
                int price = 0;
                if (rs.next()) {
                    price = rs.getInt("price");
                }

                int total = price * quantity;

                String sql = "INSERT INTO Orders (customer_id, product_id, employee_id, quantity, date_placed, total_price) VALUES (?, ?, ?, ?, NOW(), ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(customerIdField.getText().trim()));
                stmt.setInt(2, productId);
                stmt.setInt(3, Integer.parseInt(employeeIdField.getText().trim()));
                stmt.setInt(4, quantity);
                stmt.setInt(5, total);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Order placed successfully!");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });
    }
}
