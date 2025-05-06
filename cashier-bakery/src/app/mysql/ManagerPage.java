
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ManagerPage {

    private static final String URL = "jdbc:mysql://localhost:3306/Bakery";
    private static final String USER = "root";
    private static final String PASSWORD = "bakery2025";

    // Static method to show the manager page
    public static void showManagerPage() {
        JFrame frame = new JFrame("Manager Page");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(null); // required for absolute positioning
        placeComponents(panel);
        frame.add(panel);
        frame.setVisible(true);
    }

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

        JButton viewOdersBtn = new JButton("View Orders"); 
        viewOdersBtn.setBounds(200, 130, 130, 30);
        panel.add(viewOdersBtn); 



        addProductBtn.addActionListener(e -> addProduct());
        addEmployeeBtn.addActionListener(e -> addEmployee());
        viewProductsBtn.addActionListener(e -> openProductTable());
        viewEmployeesBtn.addActionListener(e -> openEmployeeTable());
        viewOdersBtn.addActionListener(e-> openOrderTable()); 


        // go back to the main page 
        JButton backButton = new JButton("Back to Main Page");
        backButton.setBounds(50, 130, 130, 30);
        panel.add(backButton);
        // back to main 
        backButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            topFrame.dispose(); // Close the current ManagerPage window
            MainPage.main(null); // Reopen the MainPage
        });
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

    private static void openOrderTable(){
        openTable("Orders");
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
