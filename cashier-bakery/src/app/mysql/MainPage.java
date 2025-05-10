import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainPage {

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        JFrame frame = new JFrame("Bakery System - Main Page");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        //this navbar is a wip, once we add order pages and view cart we can add those
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(new Color(255, 228, 196));

        ImageIcon logoIcon = new ImageIcon(MainPage.class.getResource("/resources/logo.png"));
        if (logoIcon.getIconWidth() != -1) {
            Image logoImg = logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
            logoLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
            navbar.add(logoLabel, BorderLayout.WEST);
        } else {
            System.out.println("Logo image not found :(");
            navbar.add(new JLabel("temp logo"), BorderLayout.WEST);
        }

        System.out.println("Logo path: " + MainPage.class.getResource("/resources/logo.png"));

        JLabel titleLabel = new JLabel("Cookie Corner");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        navbar.add(titleLabel, BorderLayout.CENTER);

        //update this section later for orders page
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton employeeButton = new JButton("Cashier");
        JButton managerButton = new JButton("Manager");
        buttonPanel.add(employeeButton);
        buttonPanel.add(managerButton);
        navbar.add(buttonPanel, BorderLayout.EAST);

        frame.add(navbar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome to the Bakery System!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(welcomeLabel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        ImageIcon homeIcon = new ImageIcon(MainPage.class.getResource("/resources/home.png"));
        if (homeIcon.getIconWidth() != -1) {
            Image homeImage = homeIcon.getImage().getScaledInstance(600, 300, Image.SCALE_SMOOTH);
            JLabel homeLabel = new JLabel(new ImageIcon(homeImage));
            homeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(homeLabel);
        } else {
            System.out.println("Home image not found.");
            JLabel errorLabel = new JLabel("Image not found.");
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(errorLabel);
        }

        frame.add(centerPanel, BorderLayout.CENTER);

        // UPDATED Cashier Button logic
        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputId = JOptionPane.showInputDialog(null, "Enter your Employee ID:");
                if (inputId != null && !inputId.trim().isEmpty()) {
                    try {
                        int employeeId = Integer.parseInt(inputId.trim());
                        String employeeName = getEmployeeNameById(employeeId);
                        if (employeeName != null) {
                            JOptionPane.showMessageDialog(null, "Welcome, " + employeeName + "!");
                            EmployeePage.showEmployeePage(employeeName);
                        } else {
                            JOptionPane.showMessageDialog(null, "Employee ID not found.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid ID format.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                    }
                }
            }
        });

        managerButton.addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
                String password = JOptionPane.showInputDialog(null, "Enter Manager Password:");
                if ("301".equals(password)) {
                    ManagerPage.showManagerPage();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password!", "Access Denied", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        frame.setVisible(true);
    }

    // Helper method to get employee name by ID
    private static String getEmployeeNameById(int id) throws Exception {
        String url = "jdbc:mysql://localhost:3306/Bakery";
        String user = "root";
        String password = "bakery2025";

        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT name FROM Employees WHERE employee_id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        }
        return null;
    }
}
