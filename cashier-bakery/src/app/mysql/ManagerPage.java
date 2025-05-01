// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import java.awt.event.*;
// import java.sql.*;
// import java.util.Vector;

// public class ManagerPage {

//     private static final String URL = "jdbc:mysql://localhost:3306/Bakery";
//     private static final String USER = "root";
//     private static final String PASSWORD = "bakery2025";

//     public static void main(String[] args) {
//         JFrame frame = new JFrame("Manager Dashboard");
//         frame.setSize(400, 300);
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         JPanel panel = new JPanel();
//         panel.setLayout(null);
//         frame.add(panel);

//         placeComponents(panel);

//         frame.setVisible(true);
//     }

//     private static Connection getConnection() throws SQLException, ClassNotFoundException {
//         Class.forName("com.mysql.cj.jdbc.Driver");
//         return DriverManager.getConnection(URL, USER, PASSWORD);
//     }

//     private static void placeComponents(JPanel panel) {
//         JButton addProductBtn = new JButton("Add Product");
//         addProductBtn.setBounds(50, 30, 130, 30);
//         panel.add(addProductBtn);

//         JButton addEmployeeBtn = new JButton("Add Employee");
//         addEmployeeBtn.setBounds(200, 30, 130, 30);
//         panel.add(addEmployeeBtn);

//         JButton viewProductsBtn = new JButton("View Products");
//         viewProductsBtn.setBounds(50, 80, 130, 30);
//         panel.add(viewProductsBtn);

//         JButton viewEmployeesBtn = new JButton("View Employees");
//         viewEmployeesBtn.setBounds(200, 80, 130, 30);
//         panel.add(viewEmployeesBtn);

//         addProductBtn.addActionListener(e -> addProduct());
//         addEmployeeBtn.addActionListener(e -> addEmployee());
//         viewProductsBtn.addActionListener(e -> openProductTable());
//         viewEmployeesBtn.addActionListener(e -> openEmployeeTable());
//     }

//     private static void addProduct() {
//         JTextField name = new JTextField();
//         JTextField id = new JTextField();
//         JTextField inventory = new JTextField();
//         JTextField price = new JTextField();

//         Object[] fields = {
//             "Name:", name,
//             "ProductID:", id,
//             "Inventory:", inventory,
//             "Price:", price
//         };

//         int option = JOptionPane.showConfirmDialog(null, fields, "Add Product", JOptionPane.OK_CANCEL_OPTION);
//         if (option == JOptionPane.OK_OPTION) {
//             try (Connection conn = getConnection()) {
//                 String sql = "INSERT INTO Products (ProductID, Name, Inventory, Price) VALUES (?, ?, ?, ?)";
//                 PreparedStatement stmt = conn.prepareStatement(sql);
//                 stmt.setString(1, id.getText());
//                 stmt.setString(2, name.getText());
//                 stmt.setInt(3, Integer.parseInt(inventory.getText()));
//                 stmt.setDouble(4, Double.parseDouble(price.getText()));
//                 stmt.executeUpdate();
//                 JOptionPane.showMessageDialog(null, "Product Added");
//             } catch (Exception ex) {
//                 JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
//             }
//         }
//     }

//     private static void addEmployee() {
//         JTextField id = new JTextField();
//         JTextField name = new JTextField();
//         JTextField phone = new JTextField();
//         JTextField email = new JTextField();
//         JTextField hireDate = new JTextField();

//         Object[] fields = {
//             "EmployeeID:", id,
//             "Name:", name,
//             "PhoneNumber:", phone,
//             "Email:", email,
//             "HireDate (YYYY-MM-DD):", hireDate
//         };

//         int option = JOptionPane.showConfirmDialog(null, fields, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
//         if (option == JOptionPane.OK_OPTION) {
//             try (Connection conn = getConnection()) {
//                 String sql = "INSERT INTO Employees (EmployeeID, Name, PhoneNumber, Email, HireDate) VALUES (?, ?, ?, ?, ?)";
//                 PreparedStatement stmt = conn.prepareStatement(sql);
//                 stmt.setString(1, id.getText());
//                 stmt.setString(2, name.getText());
//                 stmt.setString(3, phone.getText());
//                 stmt.setString(4, email.getText());
//                 stmt.setString(5, hireDate.getText());
//                 stmt.executeUpdate();
//                 JOptionPane.showMessageDialog(null, "Employee Added");
//             } catch (Exception ex) {
//                 JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
//             }
//         }
//     }

//     private static void openProductTable() {
//         JFrame frame = new JFrame("All Products");
//         frame.setSize(600, 400);
//         frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//         try (Connection conn = getConnection()) {
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT * FROM Products");
//             JTable table = new JTable(buildTableModel(rs));
//             JScrollPane scrollPane = new JScrollPane(table);
//             frame.add(scrollPane);

//             JButton updateButton = new JButton("Update Selected");
//             JButton deleteButton = new JButton("Delete Selected");

//             updateButton.addActionListener(e -> updateProduct(table));
//             deleteButton.addActionListener(e -> deleteProduct(table));

//             JPanel controlPanel = new JPanel();
//             controlPanel.add(updateButton);
//             controlPanel.add(deleteButton);
//             frame.add(controlPanel, "South");
//         } catch (Exception e) {
//             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//         }

//         frame.setVisible(true);
//     }

//     private static void openEmployeeTable() {
//         JFrame frame = new JFrame("All Employees");
//         frame.setSize(700, 400);
//         frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//         try (Connection conn = getConnection()) {
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT * FROM Employees");
//             JTable table = new JTable(buildTableModel(rs));
//             JScrollPane scrollPane = new JScrollPane(table);
//             frame.add(scrollPane);

//             JButton updateButton = new JButton("Update Selected");
//             JButton deleteButton = new JButton("Delete Selected");

//             updateButton.addActionListener(e -> updateEmployee(table));
//             deleteButton.addActionListener(e -> deleteEmployee(table));

//             JPanel controlPanel = new JPanel();
//             controlPanel.add(updateButton);
//             controlPanel.add(deleteButton);
//             frame.add(controlPanel, "South");
//         } catch (Exception e) {
//             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//         }

//         frame.setVisible(true);
//     }

//     private static void deleteProduct(JTable table) {
//         int row = table.getSelectedRow();
//         if (row == -1) {
//             JOptionPane.showMessageDialog(null, "Select a row first.");
//             return;
//         }
//         String id = table.getValueAt(row, 0).toString();
//         try (Connection conn = getConnection()) {
//             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Products WHERE ProductID = ?");
//             stmt.setString(1, id);
//             stmt.executeUpdate();
//             JOptionPane.showMessageDialog(null, "Deleted ProductID: " + id);
//         } catch (Exception e) {
//             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//         }
//     }

//     private static void updateProduct(JTable table) {
//         int row = table.getSelectedRow();
//         if (row == -1) {
//             JOptionPane.showMessageDialog(null, "Select a row first.");
//             return;
//         }

//         String id = table.getValueAt(row, 0).toString();
//         String name = JOptionPane.showInputDialog("New Name:", table.getValueAt(row, 1));
//         String inventory = JOptionPane.showInputDialog("New Inventory:", table.getValueAt(row, 2));
//         String price = JOptionPane.showInputDialog("New Price:", table.getValueAt(row, 3));

//         try (Connection conn = getConnection()) {
//             String sql = "UPDATE Products SET Name = ?, Inventory = ?, Price = ? WHERE ProductID = ?";
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             stmt.setString(1, name);
//             stmt.setInt(2, Integer.parseInt(inventory));
//             stmt.setDouble(3, Double.parseDouble(price));
//             stmt.setString(4, id);
//             stmt.executeUpdate();
//             JOptionPane.showMessageDialog(null, "Updated ProductID: " + id);
//         } catch (Exception e) {
//             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//         }
//     }

//     private static void deleteEmployee(JTable table) {
//         int row = table.getSelectedRow();
//         if (row == -1) {
//             JOptionPane.showMessageDialog(null, "Select a row first.");
//             return;
//         }
//         String id = table.getValueAt(row, 0).toString();
//         try (Connection conn = getConnection()) {
//             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Employees WHERE EmployeeID = ?");
//             stmt.setString(1, id);
//             stmt.executeUpdate();
//             JOptionPane.showMessageDialog(null, "Deleted EmployeeID: " + id);
//         } catch (Exception e) {
//             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//         }
//     }

//     private static void updateEmployee(JTable table) {
//         int row = table.getSelectedRow();
//         if (row == -1) {
//             JOptionPane.showMessageDialog(null, "Select a row first.");
//             return;
//         }

//         String id = table.getValueAt(row, 0).toString();
//         String name = JOptionPane.showInputDialog("New Name:", table.getValueAt(row, 1));
//         String phone = JOptionPane.showInputDialog("New Phone:", table.getValueAt(row, 2));
//         String email = JOptionPane.showInputDialog("New Email:", table.getValueAt(row, 3));
//         String hireDate = JOptionPane.showInputDialog("New Hire Date:", table.getValueAt(row, 4));

//         try (Connection conn = getConnection()) {
//             String sql = "UPDATE Employees SET Name = ?, PhoneNumber = ?, Email = ?, HireDate = ? WHERE EmployeeID = ?";
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             stmt.setString(1, name);
//             stmt.setString(2, phone);
//             stmt.setString(3, email);
//             stmt.setString(4, hireDate);
//             stmt.setString(5, id);
//             stmt.executeUpdate();
//             JOptionPane.showMessageDialog(null, "Updated EmployeeID: " + id);
//         } catch (Exception e) {
//             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//         }
//     }

//     private static DefaultTableModel buildTableModel(ResultSet rs) throws Exception {
//         ResultSetMetaData metaData = rs.getMetaData();
//         Vector<String> columnNames = new Vector<>();
//         int columnCount = metaData.getColumnCount();
//         for (int i = 1; i <= columnCount; i++) {
//             columnNames.add(metaData.getColumnName(i));
//         }

//         Vector<Vector<Object>> data = new Vector<>();
//         while (rs.next()) {
//             Vector<Object> row = new Vector<>();
//             for (int i = 1; i <= columnCount; i++) {
//                 row.add(rs.getObject(i));
//             }
//             data.add(row);
//         }

//         return new DefaultTableModel(data, columnNames);
//     }
// }

///////////////////////////////////////////////////////////////////////////////
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ManagerPage {

    private static final String URL = "jdbc:mysql://localhost:3306/Bakery";
    private static final String USER = "root";
    private static final String PASSWORD = "bakery2025";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Manager Page");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(null); // required for absolute positioning
        placeComponents(panel);
        frame.add(panel);
        frame.setVisible(true);
    }

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void placeComponents(JPanel panel) {
        JButton addProductBtn = new JButton("Add Product");
        addProductBtn.setBounds(50, 30, 130, 30);
        panel.add(addProductBtn);

        JButton addEmployeeBtn = new JButton("Add Employee");
        addEmployeeBtn.setBounds(200, 30, 130, 30);
        panel.add(addEmployeeBtn);

        JButton viewProductsBtn = new JButton("View Products");
        viewProductsBtn.setBounds(50, 80, 130, 30);
        panel.add(viewProductsBtn);

        JButton viewEmployeesBtn = new JButton("View Employees");
        viewEmployeesBtn.setBounds(200, 80, 130, 30);
        panel.add(viewEmployeesBtn);

        addProductBtn.addActionListener(e -> addProduct());
        addEmployeeBtn.addActionListener(e -> addEmployee());
        viewProductsBtn.addActionListener(e -> openProductTable());
        viewEmployeesBtn.addActionListener(e -> openEmployeeTable());
    }

    private static void addProduct() {
        JFrame frame = new JFrame("Add Product");
        frame.setSize(300, 300);
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField inventoryField = new JTextField();
        JTextField priceField = new JTextField();
        JButton save = new JButton("Save");

        panel.add(new JLabel("Product ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Inventory:"));
        panel.add(inventoryField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(save);

        frame.add(panel);
        frame.setVisible(true);

        save.addActionListener(e -> {
            try (Connection conn = getConnection()) {
                String sql = "INSERT INTO Products (ProductID, Name, Inventory, Price) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(idField.getText().trim()));
                stmt.setString(2, nameField.getText().trim());
                stmt.setInt(3, Integer.parseInt(inventoryField.getText().trim()));
                stmt.setDouble(4, Double.parseDouble(priceField.getText().trim()));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Product added!");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });
    }

    private static void addEmployee() {
        JFrame frame = new JFrame("Add Employee");
        frame.setSize(300, 300);
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField hireDateField = new JTextField();
        JButton save = new JButton("Save");

        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Hire Date (YYYY-MM-DD):"));
        panel.add(hireDateField);
        panel.add(save);

        frame.add(panel);
        frame.setVisible(true);

        save.addActionListener(e -> {
            try (Connection conn = getConnection()) {
                String sql = "INSERT INTO Employees (EmployeeID, Name, PhoneNumber, Email, HireDate) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(idField.getText().trim()));
                stmt.setString(2, nameField.getText().trim());
                stmt.setString(3, phoneField.getText().trim());
                stmt.setString(4, emailField.getText().trim());
                stmt.setString(5, hireDateField.getText().trim());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Employee added!");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });
    }

    private static void openProductTable() {
        openTable("Products");
    }

    private static void openEmployeeTable() {
        openTable("Employees");
    }

    private static void openTable(String type) {
        JFrame frame = new JFrame("View " + type);
        frame.setSize(600, 400);
        JPanel panel = new JPanel(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + type);
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

        JButton delete = new JButton("Delete");
        JButton update = new JButton("Update");

        JPanel btnPanel = new JPanel();
        btnPanel.add(delete);
        btnPanel.add(update);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String id = table.getValueAt(row, 0).toString();
                try (Connection conn = getConnection()) {
                    String sql = "DELETE FROM " + type + " WHERE " + type + "ID = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, Integer.parseInt(id));
                    stmt.executeUpdate();
                    model.removeRow(row);
                    JOptionPane.showMessageDialog(frame, type + " deleted!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        update.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                try (Connection conn = getConnection()) {
                    String id = table.getValueAt(row, 0).toString();
                    String sql;

                    if (type.equals("Products")) {
                        sql = "UPDATE Products SET Name=?, Inventory=?, Price=? WHERE ProductID=?";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, table.getValueAt(row, 1).toString().trim());
                        stmt.setInt(2, Integer.parseInt(table.getValueAt(row, 2).toString().trim()));
                        stmt.setDouble(3, Double.parseDouble(table.getValueAt(row, 3).toString().trim()));
                        stmt.setInt(4, Integer.parseInt(id));
                        stmt.executeUpdate();
                    } else {
                        sql = "UPDATE Employees SET Name=?, PhoneNumber=?, Email=?, HireDate=? WHERE EmployeeID=?";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setString(1, table.getValueAt(row, 1).toString().trim());
                        stmt.setString(2, table.getValueAt(row, 2).toString().trim());
                        stmt.setString(3, table.getValueAt(row, 3).toString().trim());
                        stmt.setString(4, table.getValueAt(row, 4).toString().trim());
                        stmt.setInt(5, Integer.parseInt(id));
                        stmt.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(frame, type + " updated!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
