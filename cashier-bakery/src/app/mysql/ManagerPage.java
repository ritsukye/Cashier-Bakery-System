import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ManagerPage {

    private static final String URL = "jdbc:mysql://localhost:3306/Bakery";
    private static final String USER = "root";
    private static final String PASSWORD = "bakery2025";

    public static void main(String[] args) {
        showManagerPage();
    }

    public static void showManagerPage() {
        JFrame frame = new JFrame("Manager Page");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
    
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(new Color(255, 228, 196));
    
        ImageIcon logoIcon = new ImageIcon(MainPage.class.getResource("/resources/logo.png"));
        if (logoIcon.getIconWidth() != -1) {
            Image logoImg = logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
            logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
            navbar.add(logoLabel, BorderLayout.WEST);
        } else {
            navbar.add(new JLabel("Logo"), BorderLayout.WEST);
        }
    
        JLabel titleLabel = new JLabel("Manager Dashboard");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        navbar.add(titleLabel, BorderLayout.CENTER);
    
        frame.add(navbar, BorderLayout.NORTH);
    
        JPanel panel = new JPanel(new GridBagLayout());
        placeComponents(panel);
        frame.add(panel, BorderLayout.CENTER);
    
        frame.setVisible(true);
    }
    

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void placeComponents(JPanel panel) {
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Padding
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Row 0
    gbc.gridx = 0;
    gbc.gridy = 0;
    JButton addProductBtn = new JButton("Add Product");
    panel.add(addProductBtn, gbc);

    gbc.gridx = 1;
    JButton addEmployeeBtn = new JButton("Add Employee");
    panel.add(addEmployeeBtn, gbc);

    // Row 1
    gbc.gridx = 0;
    gbc.gridy = 1;
    JButton viewProductsBtn = new JButton("View Products");
    panel.add(viewProductsBtn, gbc);

    gbc.gridx = 1;
    JButton viewEmployeesBtn = new JButton("View Employees");
    panel.add(viewEmployeesBtn, gbc);

    // Row 2
    gbc.gridx = 0;
    gbc.gridy = 2;
    JButton backButton = new JButton("Back to Main Page");
    panel.add(backButton, gbc);

    gbc.gridx = 1;
    JButton viewOrdersBtn = new JButton("View Orders");
    panel.add(viewOrdersBtn, gbc);

    // Action listeners
    addProductBtn.addActionListener(e -> addProduct());
    addEmployeeBtn.addActionListener(e -> addEmployee());
    viewProductsBtn.addActionListener(e -> openTable("Product"));
    viewEmployeesBtn.addActionListener(e -> openTable("Employees"));
    viewOrdersBtn.addActionListener(e -> openTable("Orders"));
    backButton.addActionListener(e -> {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        topFrame.dispose();
        MainPage.main(null);
    });
}


   

    private static void addProduct() 
    {
    JFrame frame = new JFrame("Add Product");
    frame.setSize(600, 600);
    frame.setLocationRelativeTo(null); // Center the frame on the screen

    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JTextField idField = new JTextField(15);
    JTextField nameField = new JTextField(15);
    JTextField inventoryField = new JTextField(15);
    JTextField priceField = new JTextField(15);
    JButton save = new JButton("Save");

    // Row 0: Product ID
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("Product ID:"), gbc);
    gbc.gridx = 1;
    panel.add(idField, gbc);

    // Row 1: Product Name
    gbc.gridx = 0;
    gbc.gridy = 1;
    panel.add(new JLabel("Product Name:"), gbc);
    gbc.gridx = 1;
    panel.add(nameField, gbc);

    // Row 2: Inventory
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(new JLabel("Inventory:"), gbc);
    gbc.gridx = 1;
    panel.add(inventoryField, gbc);

    // Row 3: Price
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(new JLabel("Price:"), gbc);
    gbc.gridx = 1;
    panel.add(priceField, gbc);

    // Row 4: Save Button
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(save, gbc);

    frame.add(panel);
    frame.setVisible(true);

    save.addActionListener(e -> {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO Product (product_id, product_name, inventory, price) VALUES (?, ?, ?, ?)";
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
    frame.setSize(600, 600);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLocationRelativeTo(null); // Center on screen

    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); // Padding
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.CENTER;

    JTextField idField = new JTextField(15);
    JTextField nameField = new JTextField(15);
    JTextField phoneField = new JTextField(15);
    JTextField emailField = new JTextField(15);
    JTextField hireDateField = new JTextField(15);

    JButton save = new JButton("Save");
    save.setPreferredSize(new Dimension(20, 30));


    int y = 0;

    gbc.gridx = 0; gbc.gridy = y;
    panel.add(new JLabel("Employee ID:"), gbc);
    gbc.gridx = 1;
    panel.add(idField, gbc);

    gbc.gridx = 0; gbc.gridy = ++y;
    panel.add(new JLabel("Name:"), gbc);
    gbc.gridx = 1;
    panel.add(nameField, gbc);

    gbc.gridx = 0; gbc.gridy = ++y;
    panel.add(new JLabel("Phone Number:"), gbc);
    gbc.gridx = 1;
    panel.add(phoneField, gbc);

    gbc.gridx = 0; gbc.gridy = ++y;
    panel.add(new JLabel("Email:"), gbc);
    gbc.gridx = 1;
    panel.add(emailField, gbc);

    gbc.gridx = 0; gbc.gridy = ++y;
    panel.add(new JLabel("Hire Date (YYYY-MM-DD):"), gbc);
    gbc.gridx = 1;
    panel.add(hireDateField, gbc);

    gbc.gridx = 0; gbc.gridy = ++y;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(save, gbc);

    frame.add(panel);
    frame.setVisible(true);

    save.addActionListener(e -> {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO Employees (employee_id, name, phone_number, email, hire_date) VALUES (?, ?, ?, ?, ?)";
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


    private static void openTable(String type) {
        JFrame frame = new JFrame("View " + type);
        frame.setSize(600, 400);
        JPanel panel = new JPanel(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // allow editing except ID
            }
        };

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

        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton update = new JButton("Update");
        JButton delete = new JButton("Delete");
        buttons.add(update);
        buttons.add(delete);
        panel.add(buttons, BorderLayout.SOUTH);

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            String id = table.getValueAt(row, 0).toString();
            try (Connection conn = getConnection()) {
                String key = switch (type) {
                    case "Product" -> "product_id";
                    case "Employees" -> "employee_id";
                    default -> "order_id";
                };
                String sql = "DELETE FROM " + type + " WHERE " + key + " = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, id);
                stmt.executeUpdate();
                ((DefaultTableModel) table.getModel()).removeRow(row);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        update.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            String id = table.getValueAt(row, 0).toString();
            try (Connection conn = getConnection()) {
                if (type.equals("Product")) {
                    String sql = "UPDATE Product SET inventory=?, price=? WHERE product_id=?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, Integer.parseInt(table.getValueAt(row, 1).toString().trim()));
                    stmt.setInt(2, Integer.parseInt(table.getValueAt(row, 2).toString().trim()));
                    stmt.setInt(3, Integer.parseInt(id));
                    stmt.executeUpdate();
                } else if (type.equals("Employees")) {
                    String sql = "UPDATE Employees SET name=?, phone_number=?, email=?, hire_date=? WHERE employee_id=?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, table.getValueAt(row, 1).toString().trim());
                    stmt.setString(2, table.getValueAt(row, 2).toString().trim());
                    stmt.setString(3, table.getValueAt(row, 3).toString().trim());
                    stmt.setString(4, table.getValueAt(row, 4).toString().trim());
                    stmt.setInt(5, Integer.parseInt(id));
                    stmt.executeUpdate();
                } else {
                    JOptionPane.showMessageDialog(frame, "Order updates not supported.");
                    return;
                }
                JOptionPane.showMessageDialog(frame, type + " updated!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
