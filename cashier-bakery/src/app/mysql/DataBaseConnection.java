import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/Bakery";
    private static final String USER = "root";
    private static final String PASSWORD = "bakery2025";

   private static Connection getConnection() throws SQLException, ClassNotFoundException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    return java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
}


    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("Customer Data Entry");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the panel
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // Display the window
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Create JLabel and JTextField for Name
        JLabel nameLabel = new JLabel("Customer Name:");
        nameLabel.setBounds(10, 20, 120, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(20);
        nameText.setBounds(150, 20, 200, 25);
        panel.add(nameText);

        // Create Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(150, 80, 100, 25);
        panel.add(submitButton);

        // Add ActionListener for the button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerName = nameText.getText();
                insertCustomerData(customerName);
            }
        });
    }

    private static void insertCustomerData(String customerName) {
        Random rand = new Random();
        int randNumber = rand.nextInt(500); // Generate a random customerID

        try (Connection conn = DatabaseConnection.getConnection()) {
            String insertCustomers = "INSERT INTO Customers (customerID, Name) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertCustomers)) {
                stmt.setInt(1, randNumber);
                stmt.setString(2, customerName);

                int rowsAffected = stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Inserted " + rowsAffected + " row(s) into the Customers table.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}


